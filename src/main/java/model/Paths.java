package model;

import java.util.Random;
import java.util.List;

public class Paths {

    Path paths[];

    public Paths(int numberOfPaths, List<Travel> travels)
    {
        paths = new Path[numberOfPaths];

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
            lengthOfPath = rand.nextInt(5) + 2;

            pathTravel = new Travel[lengthOfPath];

            rand = new Random();
            int lastTravel = rand.nextInt(travels.size());
            pathTravel[0] = travels.get(lastTravel);

            String lastCitysecond = travels.get(lastTravel).getCitysecond();
            int currentTravel;

            for(int i = 1; i < lengthOfPath; i++)
            {
                rand = new Random();

                while(true)
                {
                    currentTravel = rand.nextInt(travels.size());
                    
                    if(lastCitysecond.equals(travels.get(currentTravel).getCityfirst()))
                    {
                        //System.out.println(lastCitysecond + " " + travels.get(currentTravel).getCityfirst());
                        break;
                    }
                }

                lastCitysecond = travels.get(currentTravel).getCitysecond();
                pathTravel[i] = travels.get(currentTravel);
            }
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

