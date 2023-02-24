package Bildverarbeitung.dataclasses;

public class LineData {
    private double X_0, X_1, Y_0, Y_1;

    public LineData (double x0, double y0, double x1, double y1) {
        X_0 = x0;
        X_1 = x1;
        Y_0 = y0;
        Y_1 = y1;
    }

    public double[] getStart () {
        double[] point = {X_0, Y_0};
        return point;
    }

    public double[] getEnd () {
        double[] point = {X_1, Y_1};
        return point;
    }

    public String toString ()
    {
        return "("+X_0+"/"+Y_0+"|"+X_1+"/"+Y_1+")";
    }
}
