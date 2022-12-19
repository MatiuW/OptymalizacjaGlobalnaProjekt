package model;

import java.util.ArrayList;

public class ExamplePath {

    private ArrayList<String> cityFirst;
    private ArrayList<String> citySecond;
    private double sum;

    public ExamplePath(ArrayList<String> cityFirst2, ArrayList<String> citySecond2, double sum) {
        cityFirst = new ArrayList<>();
        cityFirst.addAll(cityFirst2);

        citySecond = new ArrayList<>();
        citySecond.addAll(citySecond2);

        this.sum = sum;
    }

    public ArrayList<String> getCityFirst() {
        return cityFirst;
    }

    public void setCityFirst(ArrayList<String> cityFirst1) {
        this.cityFirst = cityFirst1;
    }

    public ArrayList<String> getCitySecond() {
        return citySecond;
    }

    public void setCitySecond(ArrayList<String> citySecond2) {
        this.citySecond = citySecond2;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }
}
