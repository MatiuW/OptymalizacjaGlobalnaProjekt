package model;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class Paths {

    Path paths[];
    private int lengthOfPath;

    public Paths(int numberOfPaths, List<Travel> travels)
    {
        paths = new Path[numberOfPaths];

        lengthOfPath = (travels.size()/6) + 1;
        System.out.println("length of path: " + lengthOfPath);

        for (int i = 0; i < numberOfPaths; i++)
        {
            paths[i] = new Path(travels);
        }
        // funkcja wypisz
    }

    public void write_out()
    {
        for(int i = 0; i < paths.length; i++)
        {
            paths[i].write_out();
        }
    }
    
    private class Path
    {
        private Travel pathTravel[];
        private int lengthOfPath;

        Path(List<Travel> travels)
        {
            Random rand = new Random();
//            lengthOfPath = rand.nextInt(5) + 2;
            lengthOfPath = 6 + 1;
            String cityFirst;
            ArrayList<String> visited = new ArrayList<>();

            pathTravel = new Travel[lengthOfPath];

            rand = new Random();
            int lastTravel = rand.nextInt(travels.size());
            pathTravel[0] = travels.get(lastTravel);

            cityFirst = travels.get(lastTravel).getCityfirst();
            visited.add(travels.get(lastTravel).getCityfirst() + " " + travels.get(lastTravel).getCitysecond());

            String lastCitysecond = travels.get(lastTravel).getCitysecond();
            int currentTravel;

            for(int i = 1; i < lengthOfPath; i++)
            {
                rand = new Random();

                while(true)
                {
                    currentTravel = rand.nextInt(travels.size());

                    
                    if(lastCitysecond.equals(travels.get(currentTravel).getCityfirst()) && reapeted(visited, travels.get(currentTravel).getCityfirst(), travels.get(currentTravel).getCitysecond()))
                    {
                        //System.out.println(lastCitysecond + " " + travels.get(currentTravel).getCityfirst());
                        break;
                    }
                }

                lastCitysecond = travels.get(currentTravel).getCitysecond();
                pathTravel[i] = travels.get(currentTravel);
            }
        }

        public boolean reapeted(ArrayList<String> visited, String cF, String cS) {
            for(String s: visited) {
                String[] v = s.split(" ");

            }

            return true;
        }
        public void write_out()
        {
            System.out.println("=====================================");
            System.out.println("");

            float l = 0;

            for(int i = 0; i < lengthOfPath; i++)
            {
                System.out.println(pathTravel[i].getCityfirst() + " =====> " + pathTravel[i].getCitysecond()+" Odleglosc: " + pathTravel[i].getLength());
                l = l + Float.parseFloat(pathTravel[i].getLength());
            }
            System.out.println();
            System.out.println("Długość ścieżki: " + l);
        }
    }
}

