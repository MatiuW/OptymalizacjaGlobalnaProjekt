package selection;

import model.ExamplePath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Ranked {

    private ArrayList<ExamplePath> inputData = new ArrayList<>();
    private ArrayList<ExamplePath> outputData = new ArrayList<>();

    public Ranked(ArrayList<ExamplePath> examplePaths) {
        this.inputData = examplePaths;
    }

    public ArrayList<ExamplePath> start() {



        Random random = new Random();

        for(int i = 0; i < inputData.size(); i++) {
            for(int j = 1; j < inputData.size()- i; j++) {
                if(inputData.get(j - 1).getSum() < inputData.get(j).getSum()) {
                    Collections.swap(inputData, j - 1, j);
                }
            }
        }

        int random1 = 0;
        int random2 = 0;
        for(int i = 0; i < inputData.size(); i++) {
            random1 = random.nextInt(inputData.size());
            if (random1 != 0) {
                random2 = random.nextInt(random1);
            } else {
                random2 = 0;
            }
            outputData.add(inputData.get(random2));
        }

        return outputData;
    }


}
