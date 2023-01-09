import model.*;
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

public class Komiwojazer {

    private static final String PATH = "length.xml";
    private static final int NUMBER_OF_CITIES = 5;
    private ArrayList<ExamplePath> examplePaths = new ArrayList<>();

    public void start() throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        XmlReader xmlReader = new XmlReader();

        saxParser.parse(PATH, xmlReader);
        Travels result = xmlReader.getWebsite();
        List<Travel> travels = result.getTravelList();

        // Wygenerowanie losowych sciezek pomiedzy miastami

//        System.out.println("Wygenerowane losowe sciezki pomiedzy miastami: ");
//        System.out.println();

        Test test = new Test();
        ArrayList<String> paths = test.generuj(0);
        //mozliwe kombinacje
//        for(String p: paths) {
//            System.out.println(p);
//        }

        //redukcja - wybieranie 5 losowych sciezek
        ArrayList<String> combinations = new ArrayList<>();
        Random random = new Random();
        for(int i = 0; i < 10; i++) {
            int r = random.nextInt(paths.size());
            combinations.add(paths.get(r));
        }

        System.out.println("Wybrane sciezki");
        for(String p: combinations) {
            System.out.println(p);
        }

        //pobieranie wszystkich mozliwych miast
        ArrayList<String> nameOFCities = new ArrayList<>();
        for(int i = 0; i < travels.size(); i+=NUMBER_OF_CITIES) {
            nameOFCities.add(travels.get(i).getCityfirst());
        }


        //ukladanie sciezek
        ArrayList<String> citiesPaths = new ArrayList<>();
        StringBuilder path = new StringBuilder();

        for(int i = 0; i < combinations.size(); i++) {

            String last = "";
            for(int j = 0; j < combinations.get(i).split(" ").length; j++) {
                String[] s = combinations.get(i).split(" ");
                String first = "0";

                if(j==0) {
                    path.append(j + " " + s[j] + " ");
                    last = s[j];

                } else if(!last.equals(first)){
                    path.append( s[Integer.parseInt(last) ] + " ");
                    last = s[Integer.parseInt(last) ];
                }

            }


            citiesPaths.add(path.toString());
            path.setLength(0);
        }

        System.out.println("Wszystkie Miasta:");
        for(String noc: nameOFCities) {
            System.out.print(noc + " ");
        }
        System.out.println();

        //wypisanie wynikow
        double sum = 0;
        ArrayList<String> examplePathCityFirst = new ArrayList<>();
        ArrayList<String> examplePathCitySecond = new ArrayList<>();

        for(String cp: citiesPaths) {
            String[] spl = cp.split(" ");


            for(int i = 0; i < spl.length; i++) {

                if(i>0) {
                    examplePathCityFirst.add(nameOFCities.get(Integer.parseInt(spl[i-1])));
                    examplePathCitySecond.add(nameOFCities.get(Integer.parseInt(spl[i])));
                    for(Travel t: travels) {
                        if(t.getCityfirst().equals(nameOFCities.get(Integer.parseInt(spl[i-1]))) && t.getCitysecond().equals(nameOFCities.get(Integer.parseInt(spl[i]))) ) {
                            sum+=Double.parseDouble(t.getLength());
                        }
                    }
                }

            }

            examplePaths.add(new ExamplePath(examplePathCityFirst, examplePathCitySecond, sum));


            sum = 0;
            examplePathCityFirst.clear();
            examplePathCitySecond.clear();
        }

        writeExamplePaths();


        //selekcja turniejowa
//        Tournament tournament = new Tournament(examplePaths);
//        tournament.start();

        //selekcja rankingowa
//        Ranked ranked = new Ranked(examplePaths);
//        ArrayList<ExamplePath> rankedSelection = ranked.start();
//
//        System.out.println("Wyniki selekcji rankingowej");
//        for(ExamplePath rS: rankedSelection) {
//            System.out.println(rS.getCityFirst() + " " + rS.getCitySecond() + ":" + rS.getSum());
//        }

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
