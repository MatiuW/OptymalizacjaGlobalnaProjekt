import model.ExamplePath;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;



public class Mutation { // 2 4 5 1 2   2 1 5 4 2

    //natomiast mutacja może oznaczać wymianę w pojedynczym osobniku dwóch miast między sobą
    //http://www.imio.polsl.pl/Dopobrania/Lab%20ITK%2006%20(TSP).pdf

    private static final int n = 6;
    private static final double Pm = 0.1;

    private ArrayList<ExamplePath> inputData = new ArrayList<>();
    private ArrayList<ExamplePath> outputData = new ArrayList<>();

    public Mutation(ArrayList<ExamplePath> examplePaths) {
        this.inputData = examplePaths;
    }

    public ArrayList<ExamplePath> start() {
        Random random = new Random();
        int j = 0;

        for(ExamplePath iD: inputData) {

            int firstValue, secondValue;
            int[] help = new int[n];

            if(random.nextInt(n) < Pm) {

                firstValue = random.nextInt(n);

                do {
                    secondValue = random.nextInt(n);
                }while(secondValue == firstValue);

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


            } else {
                for(int i = 0; i<iD.getPath().length; i++) {
                    help[i] = iD.getPath()[i];
                }
            }

            outputData.add(new ExamplePath(help, 0));
            j++;
        }

        return outputData;
    }



}