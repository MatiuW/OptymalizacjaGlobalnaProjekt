import model.ExamplePath;
import model.SuccessionType;
import model.Travel;
import model.Travels;
import org.xml.sax.SAXException;
import selection.Ranked;
import selection.Tournament;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static final String[] CITIES_NUMBER = {"bialystok", "warszawa", "krakow", "wroclaw", "lodz", "poznan"};
    private static final String PATH = "length.xml";
    private static final int NUMBER_OF_GENERATIONS = 50;

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {

        //odczytywanie danych z pliku
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        XmlReader xmlReader = new XmlReader();

        saxParser.parse(PATH, xmlReader);
        Travels result = xmlReader.getWebsite();
        List<Travel> travels = result.getTravelList();

//        Komiwojazer komiwojazer = new Komiwojazer();
//        ArrayList<ExamplePath> paths = komiwojazer.createPaths();
//        paths = komiwojazer.countValues(paths, travels);//wyniki poczatkowe do sukcesji

//        System.out.println("Wyniki po zamianie na przyleglosciowa - dane wejsciowe:");
//        writeResults(paths);

        //dane wejsciowe
//        5 2 0 1 3 4  = 1713.5
//        3 4 0 5 2 1  = 1788.1
//        1 5 0 4 2 3  = 1687.6
//        4 2 5 0 1 3  = 1794.5
//        4 5 1 0 2 3  = 1472.3000000000002
//        4 5 3 0 1 2  = 1420.4
//        3 0 1 5 2 4  = 1791.6
//        4 2 3 0 5 1  = 1906.0
//        4 2 0 1 5 3  = 1456.5
//        4 3 0 2 5 1  = 2012.0

        Komiwojazer komiwojazer = new Komiwojazer();
        int[] array1= {5, 2, 0, 1, 3, 4};
        int[] array2= {3, 4, 0, 5, 2, 1};
        int[] array3= {1, 5, 0, 4, 2, 3};
        int[] array4= {4, 2, 5, 0, 1, 3};
        int[] array5= {4, 5, 1, 0, 2, 3};
        int[] array6= {4, 5, 3, 0, 1, 2};
        int[] array7= {3, 0, 1, 5, 2, 4};
        int[] array8= {4, 2, 3, 0, 5, 1};
        int[] array9= {4, 2, 0, 1, 5, 3};
        int[] array10= {4, 3, 0, 2, 5, 1};
        ArrayList<ExamplePath> paths = new ArrayList<>();
        paths.add(new ExamplePath(array1, 0));
        paths.add(new ExamplePath(array2, 0));
        paths.add(new ExamplePath(array3, 0));
        paths.add(new ExamplePath(array4, 0));
        paths.add(new ExamplePath(array5, 0));
        paths.add(new ExamplePath(array6, 0));
        paths.add(new ExamplePath(array7, 0));
        paths.add(new ExamplePath(array8, 0));
        paths.add(new ExamplePath(array9, 0));
        paths.add(new ExamplePath(array10, 0));
        paths = komiwojazer.countValues(paths, travels);
        System.out.println("dane wejsciowe:");
        writeResults(paths);

        //suckesja - streszczenie
        //dane sa nadpisywane selekcja -> mutacja(dane z selekcji) -> inwersja(dane z mutacji)->
        //-> krzyzowanie(dane z inwersji) -> ocena potomklow krzyzowanie i zapisanie wynikow
        //do danych poczatkowych
        //dane wejsciowe maja byc w formacie ArrayList<ExamplePath>
        //dane wyjsciowe w formacie ArrayList<ExamplePath>
        //metoda do oceny potomkow komiwojazer.countValues(wyniki np. krzyzowania, travels)
        //metoda do wypisania danych writeResults(wyniki np selekcji)

//            //---------------------------------------------------------------------------------------------------------
//            //selekcja - turniejowa, rankingowa, ruletka
//            Ranked ranked = new Ranked(paths);
//            ArrayList<ExamplePath> rankedSelectionResult = ranked.start();//dane z selekcji
//
//            System.out.println("Wyniki po selekcji:");
//            writeResults(rankedSelectionResult);

//        Tournament tournament = new Tournament(paths);
//        ArrayList<ExamplePath> tournamentSelectionResult = tournament.start();
//
//        System.out.println("Wyniki po selekcji turniejowej:");
//        writeResults(tournamentSelectionResult);
//
//            //---------------------------------------------------------------------------------------------------------
//            //mutacja...
//            Mutation mutation = new Mutation(paths, SuccessionType.TRIVIAL);
//            ArrayList<ExamplePath> mutationResults = mutation.start();
//            komiwojazer.countValues(mutationResults, travels);
//            //po zamianie wykorzystac algorytm zamiany do przyleglosciowej
//
//            System.out.println("Wyniki po mutacji:");
//            writeResults(mutationResults);
//
//            //---------------------------------------------------------------------------------------------------------
//            //inwersja...
//
//            //---------------------------------------------------------------------------------------------------------
//            //krzyzowanie - z wymiana krawedzi/ z wydzielaniem podtras/ heurystyczne...
//
//            WymianaKrawedzi wymianaKrawedzi = new WymianaKrawedzi(mutationResults);
//            ArrayList<ExamplePath> edgeReplacementResults = wymianaKrawedzi.start();
//            komiwojazer.countValues(edgeReplacementResults, travels);
//
//            System.out.println("Wyniki po krzyzowaniu:");
//            writeResults(edgeReplacementResults);
//
//            //---------------------------------------------------------------------------------------------------------
//            //sukcesja - trwialna/elitarna
//
//            //trwialna
//            paths = edgeReplacementResults;

        ArrayList<WriteResults> writeResults = new ArrayList<>();

        //sukcesja trywialna
//        for(int i =0; i < 3; i++) {
//            Trivial trivial = new Trivial(paths, travels);
//            writeResults.add(trivial.startSuccession());
//        }


        //sukcesja elitarna
        for(int i = 0; i < 3; i++) {
            Elite elite = new Elite(paths, travels);
            writeResults.add(elite.startSuccession());
        }

        //obliczanie sredniej z 3 uruchomien programu

        zapisSredniejDoPliku(writeResults);
    }

    public static void zapisSredniejDoPliku(ArrayList<WriteResults> daneWejsciowe) throws IOException {
        double min =0, max=0, srednia=0;
        for (WriteResults dw : daneWejsciowe) {
            min+=dw.getMin();
            max+=dw.getMax();
            srednia+=dw.getAvg();
        }

        Writer output = new BufferedWriter(new FileWriter("wyniki.txt", true));
        output.write(Math.round(srednia/daneWejsciowe.size()) + "\n");

        output.close();

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
