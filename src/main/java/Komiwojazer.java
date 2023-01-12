import model.*;
import org.xml.sax.SAXException;
import selection.Ranked;
import selection.Tournament;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.util.Random;

public class Komiwojazer {


    private static final int NUMBER_OF_CITIES = 5;
    private ArrayList<ExamplePath> examplePaths = new ArrayList<>();

    public ArrayList<ExamplePath> createPaths() throws ParserConfigurationException, SAXException, IOException {

        // Wygenerowanie losowych sciezek pomiedzy miastami
        Test test = new Test();
        ArrayList<String> paths = test.generuj(0);

        //redukcja - wybieranie 10 losowych sciezek
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

//-------------------------------
        int[] examplePathCityFirst = new int[NUMBER_OF_CITIES+1];

        for(String cp: combinations) {
            String[] spl = cp.split(" ");

            for(int i = 0; i < spl.length; i++) {
                if(i != spl.length-1) {
                    examplePathCityFirst[Integer.parseInt(spl[i])] = Integer.parseInt(spl[i+1]);
                } else {
                    examplePathCityFirst[Integer.parseInt(spl[i])] = Integer.parseInt(spl[0]);
                }
            }

            examplePaths.add(new ExamplePath(examplePathCityFirst, 0));
            Arrays.fill(examplePathCityFirst, 0);
        }
        return examplePaths;
    }

    public ArrayList<ExamplePath> countValues(ArrayList<ExamplePath> paths, List<Travel> travels) {
        for(int i = 0 ; i < paths.size(); i++) {
            double sum = 0;
            int[] path = paths.get(i).getPath();
            for(int j = 0; j < path.length - 1; j++) {
                for(Travel t: travels) {
                    if(t.getCityfirst().equals(Main.CITIES_NUMBER[path[j]])
                    && t.getCitysecond().equals(Main.CITIES_NUMBER[path[j+1]])){
                        sum+=Double.parseDouble(t.getLength());
                    }
                }
            }

            paths.get(i).setSum(sum);
            sum = 0;
        }

        return paths;
    }


}
