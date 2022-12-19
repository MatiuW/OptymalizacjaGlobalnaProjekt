package selection;

import model.ExamplePath;

import java.util.ArrayList;

public class Ranked {

    private ArrayList<ExamplePath> examplePaths = new ArrayList<>();

    public Ranked(ArrayList<ExamplePath> examplePaths) {
        this.examplePaths = examplePaths;
    }

    public void start() {
        writeExamplePaths();
    }

    public void writeExamplePaths() {
        System.out.println();
        for(ExamplePath ep: examplePaths) {

            System.out.print("Miasta poczatkowe: ");
            for(String cf: ep.getCityFirst()) {
                System.out.print(cf + " ");
            }
            System.out.println();
            System.out.print("Miasta docelowe:   ");
            for(String cs: ep.getCitySecond()) {
                System.out.print(cs + " ");
            }
            System.out.println();

            System.out.println("Suma: " + ep.getSum());
            System.out.println();
        }
    }
}
