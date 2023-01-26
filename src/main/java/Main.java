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
import java.io.IOException;
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

        Komiwojazer komiwojazer = new Komiwojazer();
        ArrayList<ExamplePath> paths = komiwojazer.createPaths();
        paths = komiwojazer.countValues(paths, travels);//wyniki poczatkowe do sukcesji

        System.out.println("Wyniki po zamianie na przyleglosciowa - dane wejsciowe:");
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


        //sukcesja trywialna
//        Trivial trivial = new Trivial(paths, travels);
//        trivial.startSuccession();

        //sukcesja elitarna
        Elite elite = new Elite(paths, travels);
        elite.startSuccession();
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
