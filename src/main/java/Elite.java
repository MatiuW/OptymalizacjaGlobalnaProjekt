import model.ExamplePath;
import model.SuccessionType;
import model.Travel;
import selection.Ranked;
import selection.Tournament;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Elite {
    private ArrayList<ExamplePath> inputData = new ArrayList<>();
    private static final int NUMBER_OF_GENERATIONS = 100;
    private List<Travel> travels;

    private ArrayList<ExamplePath> rankedSelectionResult;
    private ArrayList<ExamplePath> mutationResults;
    private ArrayList<ExamplePath> edgeReplacementResults;

    public Elite(ArrayList<ExamplePath> examplePaths, List<Travel> travels) {
        this.travels = travels;
        this.inputData = examplePaths;
    }

    public void startSuccession() {
        System.out.println("Sukcesja elitarna");
        for(int i = 0 ; i < NUMBER_OF_GENERATIONS; i++) {
            System.out.println("Pokolenie nr: " + i);
            System.out.println("Dane wejsciowe:");
            writeResults(inputData);
            //---------------------------------------------------------------------------------------------------------
            //selekcja - turniejowa, rankingowa, ruletka
            Ranked ranked = new Ranked(inputData);
            rankedSelectionResult = ranked.start();//dane z selekcji

            System.out.println("Wyniki selekcji:");
            writeResults(rankedSelectionResult);

//            Tournament tournament = new Tournament(paths);
//            ArrayList<ExamplePath> tournamentSelectionResult = tournament.start();
//
//            System.out.println("Wyniki po selekcji turniejowej:");
//            writeResults(tournamentSelectionResult);

            //---------------------------------------------------------------------------------------------------------
            //mutacja...
            Mutation mutation = new Mutation(rankedSelectionResult, SuccessionType.ELITE);
            ArrayList<ExamplePath> mutationResults = mutation.start();
            new Komiwojazer().countValues(mutationResults, travels);

            System.out.println("Wyniki mutacji:");
            writeResults(mutationResults);

            //---------------------------------------------------------------------------------------------------------
            //inwersja...

            //---------------------------------------------------------------------------------------------------------
            //krzyzowanie - z wymiana krawedzi/ z wydzielaniem podtras/ heurystyczne...

            WymianaKrawedzi wymianaKrawedzi = new WymianaKrawedzi(rankedSelectionResult, SuccessionType.ELITE);
            ArrayList<ExamplePath> edgeReplacementResults = wymianaKrawedzi.start();
            new Komiwojazer().countValues(edgeReplacementResults, travels);

            System.out.println("Wyniki krzyzowaniu:");
            writeResults(edgeReplacementResults);

            //---------------------------------------------------------------------------------------------------------
            //sukcesja - elitarna
            ArrayList<ExamplePath> elitarna = new ArrayList<>();
            //laczenie list

            elitarna.addAll(inputData);//dane wejsciowe
            elitarna.addAll(mutationResults);//wyniki mutacji
//            elitarna.addAll(wynikiInwersji);//wyniki inwersji
            elitarna.addAll(edgeReplacementResults);

//            System.out.println("elitarna");
//            for (DaneWyjsciowe dw : elitarna) {
//                System.out.println(dw.getBinarny() + " " + dw.getRastrigina());
//            }
            //tutaj zamienic na inny typ sukcesji

            //sortowanie

            for (int j = 0; j < elitarna.size(); j++){
                for (int k = 1; k < elitarna.size() - j; k++) {
                    if(elitarna.get(k-1).getSum() > elitarna.get(k).getSum()) {
                        Collections.swap(elitarna, k-1, k);
                    }
                }
            }

            elitarna.subList(inputData.size(), elitarna.size()).clear();

            inputData = elitarna;
        }

        System.out.println("Wyniki ostateczne:");
        writeResults(inputData);
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
