import model.ExamplePath;
import model.SuccessionType;
import model.Travel;
import selection.Ranked;
import selection.Tournament;

import java.util.ArrayList;
import java.util.List;

public class Trivial {

    private ArrayList<ExamplePath> inputData = new ArrayList<>();
    private static final int NUMBER_OF_GENERATIONS = 5;
    private List<Travel> travels;

    public Trivial(ArrayList<ExamplePath> examplePaths, List<Travel> travels) {
        this.travels = travels;
        this.inputData = examplePaths;
    }

    public void startSuccession() {
        System.out.println("Sukcesja trywialna");
        for(int i = 0 ; i < NUMBER_OF_GENERATIONS; i++) {
            System.out.println("Pokolenie nr: " + i);
            System.out.println("Dane wejsciowe:");
            writeResults(inputData);
            //---------------------------------------------------------------------------------------------------------
            //selekcja - rankingowa,
            Ranked ranked = new Ranked(inputData);
            ArrayList<ExamplePath> rankedSelectionResult = ranked.start();//dane z selekcji

            System.out.println("Wyniki po selekcji:");
            writeResults(rankedSelectionResult);

            //turniejowa
//            Tournament tournament = new Tournament(inputData);
//            ArrayList<ExamplePath> tournamentSelectionResult = tournament.start();
//
//            System.out.println("Wyniki po selekcji turniejowej:");
//            writeResults(tournamentSelectionResult);

            //---------------------------------------------------------------------------------------------------------
            //mutacja...
            Mutation mutation = new Mutation(rankedSelectionResult, SuccessionType.TRIVIAL);
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
            //sukcesja - trwialna/elitarna

            //trwialna
            inputData = edgeReplacementResults;
        }
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
