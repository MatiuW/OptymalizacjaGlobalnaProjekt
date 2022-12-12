package model;

import java.util.Random;
import java.util.List;

import java.util.ArrayList;
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
        private List<Travel> pathTravel;
        private int lengthOfPath;

        Path(List<Travel> travels)
        {

            Random rand = new Random();
            lengthOfPath = 6;

            pathTravel = new ArrayList();

            rand = new Random();

            int lastTravel = rand.nextInt(travels.size());
            pathTravel.add(travels.get(lastTravel));

            for(int i = 0; i < travels.size(); i++)
            {
                if(travels.get(lastTravel).getCityfirst().equals(travels.get(i).getCityfirst()) && travels.get(lastTravel).getCitysecond().equals(travels.get(i).getCitysecond())) travels.remove(i);
                if(travels.get(lastTravel).getCityfirst().equals(travels.get(i).getCitysecond()) && travels.get(lastTravel).getCitysecond().equals(travels.get(i).getCityfirst())) travels.remove(i);
            }

            String lastCitysecond = travels.get(lastTravel).getCitysecond();
            int currentTravel;

            for(int i = 1; i < lengthOfPath; i++)
            {
                rand = new Random();

                while(true)
                {
                    currentTravel = rand.nextInt(travels.size());
                    
                    if(lastCitysecond.equals(travels.get(currentTravel).getCityfirst())) break;
                }

                lastCitysecond = travels.get(currentTravel).getCitysecond();
                pathTravel.add(travels.get(currentTravel));

                for(int q = 0; i < travels.size(); q++)
                {
                    if(travels.get(currentTravel).getCityfirst().equals(travels.get(q).getCityfirst()) && travels.get(currentTravel).getCitysecond().equals(travels.get(q).getCitysecond()))
                    {
                        travels.remove(q);
                    }
                    if(travels.get(currentTravel).getCityfirst().equals(travels.get(q).getCitysecond()) && travels.get(currentTravel).getCitysecond().equals(travels.get(q).getCityfirst()))
                    {
                        travels.remove(q);
                    }
                }
            }
        }
        public void write_out()
        {
            System.out.println("=====================================");
            System.out.println("");

            float l = 0;

            for(int i = 0; i < lengthOfPath; i++)
            {
                System.out.println(pathTravel.get(i).getCityfirst() + " =====> " + pathTravel.get(i).getCitysecond()+" Odleglosc: " + pathTravel.get(i).getLength());
                l = l + Float.parseFloat(pathTravel.get(i).getLength());
            }
            System.out.println();
            System.out.println("Długość ścieżki: " + l);
        }
    }
}

