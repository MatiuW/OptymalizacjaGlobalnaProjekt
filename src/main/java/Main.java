import model.ExamplePath;
import model.Travel;
import model.Travels;
import org.xml.sax.SAXException;
import selection.Ranked;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static final String[] CITIES_NUMBER = {"bialystok", "warszawa", "krakow", "wroclaw", "lodz", "poznan"};
    private static final String PATH = "length.xml";

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

        System.out.println("Wyniki po zamianie na przyleglosciowa:");
        writeResults(paths);

        //suckesja - streszczenie
        //dane sa nadpisywane selekcja -> mutacja(dane z selekcji) -> inwersja(dane z mutacji)->
        //-> krzyzowanie(dane z inwersji) -> ocena potomklow krzyzowanie i zapisanie wynikow
        //do danych poczatkowych
        //dane wejsciowe maja byc w formacie ArrayList<ExamplePath>
        //dane wyjsciowe w formacie ArrayList<ExamplePath>
        //metoda do oceny potomkow komiwojazer.countValues(wyniki np. krzyzowania, travels)
        //metoda do wypisania danych writeResults(wyniki np selekcji)


        //---------------------------------------------------------------------------------------------------------
        //selekcja - turniejowa, rankingowa, ruletka
        Ranked ranked = new Ranked(paths);
        ArrayList<ExamplePath> rankedSelectionResult = ranked.start();//dane z selekcji

        System.out.println("Wyniki po selekcji:");
        writeResults(rankedSelectionResult);

        //---------------------------------------------------------------------------------------------------------
        //mutacja...
        Mutation mutation = new Mutation(rankedSelectionResult);
        ArrayList<ExamplePath> mutationResults = mutation.start();
        komiwojazer.countValues(mutationResults, travels);

        System.out.println("Wyniki po mutacji:");
        writeResults(mutationResults);
        //---------------------------------------------------------------------------------------------------------
        //inwersja...

        //---------------------------------------------------------------------------------------------------------
        //krzyzowanie - z wymiana krawedzi/ z wydzielaniem podtras/ heurystyczne...

        //---------------------------------------------------------------------------------------------------------
        //ocena potomkow za pomoca  komiwojazer.countValues(wyniki krzyzowania, travels);
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
