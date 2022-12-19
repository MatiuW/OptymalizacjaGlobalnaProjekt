import model.Test;
import model.Travel;
import model.Travels;
import model.Paths;
import org.xml.sax.SAXException;

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

    public void start() throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        XmlReader xmlReader = new XmlReader();

        saxParser.parse(PATH, xmlReader);
        Travels result = xmlReader.getWebsite();
        List<Travel> travels = result.getTravelList();

        // Wygenerowanie losowych sciezek pomiedzy miastami

        System.out.println("Wygenerowane losowe sciezki pomiedzy miastami: ");
        System.out.println();

        Test test = new Test();
        ArrayList<String> paths = test.generuj(0);
        //mozliwe kombinacje
//        for(String p: paths) {
//            System.out.println(p);
//        }

        //redukcja - wybieranie 5 losowych sciezek
        ArrayList<String> combinations = new ArrayList<>();
        Random random = new Random();
        for(int i = 0; i < 2; i++) {
            int r = random.nextInt(paths.size());
            combinations.add(paths.get(r));
        }

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
        double sum = 0;

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

        System.out.println("Miasta:");
        for(String noc: nameOFCities) {
            System.out.print(noc + " ");
        }
        System.out.println();

        //wypisanie wynikow
        for(String cp: citiesPaths) {
            System.out.println("============= sciezka =============");
            String[] spl = cp.split(" ");
//            for(int i = 0; i < spl.length-1; i+=2) {
//                System.out.println(spl[i] + " " + spl[i+1]);
//            }

//            for(int i = 0; i < spl.length-1; i+=2) {
//                System.out.println(spl[i] + " " + spl[i+1]);
//                System.out.println(nameOFCities.get(Integer.parseInt(spl[i])) + " " + nameOFCities.get(Integer.parseInt(spl[i+1])));
//            }

            for(int i = 0; i < spl.length; i++) {
                System.out.print(spl[i] + " - " + nameOFCities.get(Integer.parseInt(spl[i])) + ", ");
            }

            System.out.println();
        }
    }

}
