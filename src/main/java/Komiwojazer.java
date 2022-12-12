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

        //redukcja
        ArrayList<String> combinations = new ArrayList<>();
        Random random = new Random();
        for(int i = 0; i < 5; i++) {
            int r = random.nextInt(paths.size());
            combinations.add(paths.get(r));
        }

//        for(String p: combinations) {
//            System.out.println(p);
//        }

        //pobieranie nazw miast
        ArrayList<String> nameOFCities = new ArrayList<>();
        for(int i = 0; i < travels.size(); i+=NUMBER_OF_CITIES) {
            nameOFCities.add(travels.get(i).getCityfirst());
        }

//        for(String noc: nameOFCities) {
//            System.out.println(noc);
//        }

        //ukladanie sciezek
        ArrayList<String> citiesPaths = new ArrayList<>();
        StringBuilder path = new StringBuilder();
        double sum = 0;

        String firstCity= "";
        String lastCity = "";
        for(int i = 0; i < combinations.size(); i++) {

            String[] cityValue = combinations.get(i).split(" ");

            for(int j = 0; j < cityValue.length-1; j++) {
                String cf = nameOFCities.get(Integer.parseInt(cityValue[j]));
                String cs = nameOFCities.get(Integer.parseInt(cityValue[j+1]));

//                System.out.print(cf  + "--->" + cs + ":");
                //szukanie odpowiadajacej wartosci
                for(Travel t: travels) {
                    if(t.getCityfirst().equals(cf) && t.getCitysecond().equals(cs)) {
                        if(j == 0 && firstCity.equals("")) {
                            firstCity = t.getCityfirst();
                        }

                        if(j == cityValue.length-2 && lastCity.equals("")) {
                            lastCity = t.getCitysecond();
                        }

                        path.append(t.getCityfirst()).append("-->").append(t.getCitysecond()).append("\n");
                        sum+=Double.parseDouble(t.getLength());
                    }
                }
            }

//            System.out.println(firstCity + " " + lastCity);

            //dodanie powrotu
            for(Travel t: travels) {
                if(t.getCityfirst().equals(lastCity) && t.getCitysecond().equals(firstCity)) {
                    path.append(t.getCityfirst()).append("-->").append(t.getCitysecond()).append("\n");
                    sum+=Double.parseDouble(t.getLength());
                }
            }


            citiesPaths.add(path + String.valueOf(sum));
            sum = 0;
            path.setLength(0);
            firstCity = "";
            lastCity = "";

        }

        //dodanie wartosci powracajacej do 1 miasta
//        for(String cp: citiesPaths) {
//            System.out.println(cp);
//        }


        //wypisanie wynikow
        for(String cp: citiesPaths) {
            System.out.println(cp);
        }
    }

}
