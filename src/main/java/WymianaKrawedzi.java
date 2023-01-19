import model.ExamplePath;

import java.util.*;

public class WymianaKrawedzi {

    private ArrayList<ExamplePath> inputData = new ArrayList<>();
    private ArrayList<ExamplePath> outputData = new ArrayList<>();

    private static final int n = 6;

    private static final double Pk = 0.1;

    public WymianaKrawedzi(ArrayList<ExamplePath> examplePaths) {
        this.inputData = examplePaths;
    }

    public ArrayList<ExamplePath> start() {

        ArrayList<String> pars = new ArrayList<>();
        Random random = new Random();
        int valueFirst;
        int valueSecond;

        //wybieranie par do krzyzowania jesli parzysta ilosc
        if(inputData.size() %2 == 0) {

            for(int i = 0; i < inputData.size()/2; i++) {

                if(random.nextDouble() < Pk) {
                    //sprawdzenie czy dane first i second juz sa w tabeli
                    //sprawdzenie czy first i second sa rozne

                    do {
                        valueFirst = random.nextInt(inputData.size());
                        valueSecond = random.nextInt(inputData.size());
                    }while(check(pars, valueFirst, valueSecond));



                    pars.add(valueFirst + " " + valueSecond);
                }
            }

            for(String p: pars) {
                System.out.println("para: " + p);
            }
        }

        //wybieranie par do krzyzowania jesli nieparzysta ilosc
        if(inputData.size() %2 != 0) {

            for(int i = 0; i < inputData.size()/2 - 1; i++) {

                if(random.nextDouble() < Pk) {
                    do {
                        valueFirst = random.nextInt(inputData.size());
                        valueSecond = random.nextInt(inputData.size());
                    }while(check(pars, valueFirst, valueSecond));

                    pars.add(valueFirst + " " + valueSecond);
                }
            }

            for(String p: pars) {
                System.out.println("para: " + p);
            }
        }

        //dodanie pozostałych rodzicow do listy
        ArrayList<Integer> allPars = new ArrayList<>();
        for(String p: pars) {
            String[] values = p.split(" ");
            allPars.add(Integer.parseInt(values[0]));
            allPars.add(Integer.parseInt(values[1]));
        }

        for(int i = 0 ; i < inputData.size(); i++) {
            if(!allPars.contains(i)) {
//                System.out.println("nie ma: " + i);
                outputData.add(inputData.get(i));
            }
        }

        //tworzenie potomkow dla kazdej pary

        //tworzenie pierwszego potomka dla kazdej pary
        int[] examplePathCityFirst = new int[n];
        Arrays.fill(examplePathCityFirst, -1);

        int helpValue = 0;
        int checkNumberToSet;

        for(String p: pars) {
            ExamplePath childFirst = inputData.get(Integer.parseInt(p.split(" ")[0]));
            ExamplePath childSecond = inputData.get(Integer.parseInt(p.split(" ")[1]));

            for(int i = 0; i < childFirst.getPath().length; i++) {

                if(i==0) {
                    examplePathCityFirst[i] =  childFirst.getPath()[i];
                    helpValue =  childFirst.getPath()[i];
                }

                if(i%2==0 && i!=0) {//pobranie danych z 1 potomka

                        System.out.println("tu 1: " + helpValue + " " + childFirst.getPath()[helpValue]);
                        //helpValue nie moze byc rowny wartosci w examplePathCityFirst
                        if(checkRepicateValues(examplePathCityFirst[helpValue], childFirst.getPath()[helpValue], examplePathCityFirst)) {
                                //szuka nowej wartosci do wstawienia ktorej jeszcze nie ma w liscie
                                checkNumberToSet = findMissing(examplePathCityFirst);
                                helpValue = checkPlaceToSet(examplePathCityFirst);

                                examplePathCityFirst[helpValue] = checkNumberToSet;
                                System.out.println("w miejsce " + helpValue + " zostala dodana wartosc: " + checkNumberToSet);
                        } else {
                            examplePathCityFirst[helpValue] = childFirst.getPath()[helpValue];
                            helpValue = childFirst.getPath()[helpValue];
                        }
                }

                if(i%2!=0 && i!=0) {//pobranie danych z 2 potomka
                        System.out.println("tu2: " + helpValue + " " + childSecond.getPath()[helpValue]);

                        //sprawdza czy jest skonczony cykl
                    if(checkRepicateValues(examplePathCityFirst[helpValue], childSecond.getPath()[helpValue], examplePathCityFirst)) {
                        //szuka nowej wartosci do wstawienia ktorej jeszcze nie ma w liscie
                        checkNumberToSet = findMissing(examplePathCityFirst);
                        helpValue = checkPlaceToSet(examplePathCityFirst);

                        examplePathCityFirst[helpValue] = checkNumberToSet;
                        System.out.println("w miejsce " + helpValue + " zostala dodana wartosc: " + checkNumberToSet);

                    } else {
                        examplePathCityFirst[helpValue] = childSecond.getPath()[helpValue];
                        helpValue = childSecond.getPath()[helpValue];
                    }



                }
            }

            outputData.add(new ExamplePath(examplePathCityFirst, 0));
            Arrays.fill(examplePathCityFirst, -1);
        }

        //------------------------------------------------------------------------------------------------------------
        //tworzenie 2 potomka

        examplePathCityFirst = new int[n];
        Arrays.fill(examplePathCityFirst, -1);

        helpValue = 0;

        for(String p: pars) {
            ExamplePath childFirst = inputData.get(Integer.parseInt(p.split(" ")[1]));
            ExamplePath childSecond = inputData.get(Integer.parseInt(p.split(" ")[0]));

            for(int i = 0; i < childFirst.getPath().length; i++) {

                if(i==0) {
                    examplePathCityFirst[i] =  childFirst.getPath()[i];
                    helpValue =  childFirst.getPath()[i];
                }

                if(i%2==0 && i!=0) {//pobranie danych z 1 potomka

                    System.out.println("tu 1: " + helpValue + " " + childFirst.getPath()[helpValue]);
                    //helpValue nie moze byc rowny wartosci w examplePathCityFirst
                    if(checkRepicateValues(examplePathCityFirst[helpValue], childFirst.getPath()[helpValue], examplePathCityFirst)) {
                        //szuka nowej wartosci do wstawienia ktorej jeszcze nie ma w liscie
                        checkNumberToSet = findMissing(examplePathCityFirst);
                        helpValue = checkPlaceToSet(examplePathCityFirst);

                        examplePathCityFirst[helpValue] = checkNumberToSet;
                        System.out.println("w miejsce " + helpValue + " zostala dodana wartosc: " + checkNumberToSet);
                    } else {
                        examplePathCityFirst[helpValue] = childFirst.getPath()[helpValue];
                        helpValue = childFirst.getPath()[helpValue];
                    }
                }

                if(i%2!=0 && i!=0) {//pobranie danych z 2 potomka
                    System.out.println("tu2: " + helpValue + " " + childSecond.getPath()[helpValue]);

                    //sprawdza czy jest skonczony cykl
                    if(checkRepicateValues(examplePathCityFirst[helpValue], childSecond.getPath()[helpValue], examplePathCityFirst)) {
                        //szuka nowej wartosci do wstawienia ktorej jeszcze nie ma w liscie
                        checkNumberToSet = findMissing(examplePathCityFirst);
                        helpValue = checkPlaceToSet(examplePathCityFirst);

                        examplePathCityFirst[helpValue] = checkNumberToSet;
                        System.out.println("w miejsce " + helpValue + " zostala dodana wartosc: " + checkNumberToSet);

                    } else {
                        examplePathCityFirst[helpValue] = childSecond.getPath()[helpValue];
                        helpValue = childSecond.getPath()[helpValue];
                    }



                }
            }

            outputData.add(new ExamplePath(examplePathCityFirst, 0));
            Arrays.fill(examplePathCityFirst, -1);
        }

        return outputData;
    }

    public boolean check(ArrayList<String> pars, int valueFirst, int valueSecond) {

        for(String p: pars) {
            String[] c = p.split(" ");

            //warunek ktory nie moze sie spelnic
            if(Integer.parseInt(c[0]) == valueFirst || Integer.parseInt(c[1]) == valueFirst ||
                    Integer.parseInt(c[0]) == valueSecond || Integer.parseInt(c[1]) == valueSecond ||
                    valueSecond == valueFirst) {
                return true;
            }

        }

        return false;
    }

    public boolean checkRepicateValues(int place, int value, int[] examplePathCityFirst) {

        boolean contain = false;

        //sprawdza czy w miejscu w ktorym chce dodac wartosc juz jakas jest
        if (place != -1) {
            contain = true;
        }

        //sprawdza czy mozna dodac wartosc czy moze juz jest na liscie - value
        for(int i = 0; i < examplePathCityFirst.length; i++) {
            if(examplePathCityFirst[i] == value) {
                contain = true;
            }
        }


        return contain;//jesli wszystko git
    }

    public int findMissing(int[] examplePathCityFirst) {
        ArrayList<Integer> aL = new ArrayList<>();
        for(int exPCF: examplePathCityFirst) {
            aL.add(exPCF);
        }

        Collections.sort(aL);


        boolean contain = false;
        for(int i =0; i < aL.size(); i++) {
            contain = false;

            for(int a: aL){
                if(i == a) {
                    contain = true;
                }
            }

            if(!contain) {
                return i;
            }
        }

        return 0;
    }

    public int checkPlaceToSet(int[] examplePathCityFirst) {
        for(int i = 0; i < examplePathCityFirst.length; i++) {
            if(examplePathCityFirst[i] == -1) {
                return i;
            }
        }

        return 0;
    }
}
