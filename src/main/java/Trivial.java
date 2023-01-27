import model.ExamplePath;
import model.SuccessionType;
import model.Travel;
import selection.Ranked;
import selection.Tournament;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class Trivial {

    private ArrayList<ExamplePath> inputData = new ArrayList<>();
    private static final int NUMBER_OF_GENERATIONS = 10;
    private List<Travel> travels;

    public Trivial(ArrayList<ExamplePath> examplePaths, List<Travel> travels) {
        this.travels = travels;
        this.inputData = examplePaths;
    }

    public WriteResults startSuccession() throws IOException {
        System.out.println("Sukcesja trywialna");
        for(int i = 0 ; i < NUMBER_OF_GENERATIONS; i++) {
            System.out.println("Pokolenie nr: " + i);
            System.out.println("Dane wejsciowe:");
            writeResults(inputData);
            //---------------------------------------------------------------------------------------------------------
            //selekcja - rankingowa,
//            Ranked ranked = new Ranked(inputData);
//            ArrayList<ExamplePath> selectionResult = ranked.start();//dane z selekcji
//
//            System.out.println("Wyniki po selekcji:");
//            writeResults(rankedSelectionResult);

            //turniejowa
            Tournament tournament = new Tournament(inputData);
            ArrayList<ExamplePath> selectionResult = tournament.start();

            System.out.println("Wyniki po selekcji turniejowej:");
            writeResults(selectionResult);

            //---------------------------------------------------------------------------------------------------------
            //mutacja...
            Mutation mutation = new Mutation(selectionResult, SuccessionType.TRIVIAL);
            ArrayList<ExamplePath> mutationResults = mutation.start();
            new Komiwojazer().countValues(mutationResults, travels);
            //po zamianie wykorzystac algorytm zamiany do przyleglosciowej

            System.out.println("Wyniki po mutacji:");
            writeResults(mutationResults);

            //---------------------------------------------------------------------------------------------------------
            //inwersja...

            //---------------------------------------------------------------------------------------------------------
            //krzyzowanie - z wymiana krawedzi/ z wydzielaniem podtras/ heurystyczne...

            WymianaKrawedzi wymianaKrawedzi = new WymianaKrawedzi(mutationResults, SuccessionType.TRIVIAL);
            ArrayList<ExamplePath> edgeReplacementResults = wymianaKrawedzi.start();
            new Komiwojazer().countValues(edgeReplacementResults, travels);

            System.out.println("Wyniki po krzyzowaniu:");
            writeResults(edgeReplacementResults);

            //---------------------------------------------------------------------------------------------------------
            //sukcesja - trwialna

            //trwialna
            inputData = edgeReplacementResults;
        }

        //input data save to file
        return zapisSredniejDoPliku(inputData);
    }

    public WriteResults zapisSredniejDoPliku(ArrayList<ExamplePath> daneWejsciowe)  {
        double min = daneWejsciowe.get(0).getSum(), max = daneWejsciowe.get(0).getSum(), srednia;
        double sum = 0;
        for (ExamplePath dw : daneWejsciowe) {
            if(dw.getSum() < min) {
                min = dw.getSum();
            }

            if(dw.getSum() > max) {
                max = dw.getSum();
            }
            sum += dw.getSum();
        }
        srednia = sum/daneWejsciowe.size();


        return new WriteResults(min, max, srednia);
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
