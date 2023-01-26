package selection;

import model.ExamplePath;

import java.util.ArrayList;
import java.util.Random;

public class Tournament {

    private ArrayList<ExamplePath> inputData = new ArrayList<>();
    private ArrayList<ExamplePath> outputData = new ArrayList<>();

    public Tournament(ArrayList<ExamplePath> inputData) {
        this.inputData = inputData;
    }

    public ArrayList<ExamplePath> start() {
        Random random = new Random();
        int groupSize=random.nextInt(inputData.size()-1) + 1;
        ArrayList<Integer> group = new ArrayList<>();

        int possibleAddNumber = 0;

        for(int i = 0; i < inputData.size(); i++) {
            //wybor czlonkow grupy
            for(int j = 0; j < groupSize; j++) {
                do {
                    possibleAddNumber = random.nextInt(inputData.size());
                }while(checkSameValues(group, possibleAddNumber));
                group.add(possibleAddNumber);
            }
            System.out.println("czlonkowie grupy: ");
            for(int j = 0; j < group.size(); j++) {
                System.out.print(group.get(j) + " ");
            }
            System.out.println();

            outputData.add(findMin(group));

            group.clear();
        }

         return outputData;
    }

    public boolean checkSameValues(ArrayList<Integer> group, int newValue) {
        for(int g: group) {
            if(newValue == g) {
                return true;
            }
        }

        return false;
    }

    public ExamplePath findMin(ArrayList<Integer> group) {
        int possibleValueReturn = -1;

        for(int g: group) {
            possibleValueReturn = group.get(0);

            if(inputData.get(possibleValueReturn).getSum() > inputData.get(g).getSum()) {
                possibleValueReturn = g;
            }
        }
        System.out.println("minimum to: " + possibleValueReturn);

        return inputData.get(possibleValueReturn);
    }
}
