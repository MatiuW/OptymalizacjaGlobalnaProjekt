import model.ExamplePath;
import model.SuccessionType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class Mutation {

    //natomiast mutacja może oznaczać wymianę w pojedynczym osobniku dwóch miast między sobą
    //http://www.imio.polsl.pl/Dopobrania/Lab%20ITK%2006%20(TSP).pdf

    private static final int n = 6;
    private static final double Pm = 0.1;

    private ArrayList<ExamplePath> inputData = new ArrayList<>();
    private ArrayList<ExamplePath> outputData = new ArrayList<>();
    private SuccessionType successionType;

    public Mutation(ArrayList<ExamplePath> examplePaths, SuccessionType successionType) {
        this.inputData = examplePaths;
        this.successionType = successionType;
    }

    public ArrayList<ExamplePath> start() {
        Random random = new Random();

        int h = 0;
        for(ExamplePath iD: inputData) {

            int firstValue, secondValue;
            int[] help = new int[n];

            if(random.nextDouble() < Pm) {

                firstValue = random.nextInt(n);

                do {
                    secondValue = random.nextInt(n);
                }while(secondValue == firstValue);

                System.out.println("Pierwsza wartosc: " + firstValue + " druga wartosc: " + secondValue +
                        "dla h = " + h);

                for(int i = 0; i<iD.getPath().length; i++) {
                    if(i == secondValue) {
                        help[i] = iD.getPath()[firstValue];
                    }
                    if(i == firstValue) {
                        help[i] = iD.getPath()[secondValue];
                    }

                    if(i != firstValue && i != secondValue) {
                        help[i] = iD.getPath()[i];
                    }
                }

                outputData.add(new ExamplePath(help, 0));

            } else if(successionType.equals(SuccessionType.TRIVIAL)){
                for(int i = 0; i<iD.getPath().length; i++) {
                    help[i] = iD.getPath()[i];
                }
                outputData.add(new ExamplePath(help, 0));
            }


        }

        //zamiana na przyleglosciowa

//        System.out.println("Dane testowe");
//        writeResults(outputData);


        int[] examplePathCityFirst = new int[n];
        ArrayList<ExamplePath> outputDataTwo = new ArrayList<>();

        for(ExamplePath ep: outputData) {
            int[] spl = ep.getPath();

            for(int i = 0; i < spl.length; i++) {
                if(i != spl.length-1) {
                    examplePathCityFirst[spl[i]] = spl[i+1];
                } else {
                    examplePathCityFirst[spl[i]] = spl[0];
                }
            }

            outputDataTwo.add(new ExamplePath(examplePathCityFirst, 0));
            Arrays.fill(examplePathCityFirst, 0);
        }

        return outputDataTwo;
    }

    public static void writeResults(ArrayList<ExamplePath> paths){
        for(ExamplePath path: paths) {
            for(int p: path.getPath()) {
                System.out.print(p + " ");
            }
            System.out.print(" = " + path.getSum() + "\n");
        }
    }

}