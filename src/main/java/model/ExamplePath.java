package model;

import java.util.ArrayList;

public class ExamplePath {

    private int[] path;
    private double sum;

    public ExamplePath(int[] path, double sum) {
        this.path = new int[path.length];
        for(int i = 0; i < path.length; i++){
            this.path[i] = path[i];
        }
        this.sum = sum;
    }

    public int[] getPath() {
        return path;
    }

    public void setPath(int[] path) {
        this.path = path;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }
}
