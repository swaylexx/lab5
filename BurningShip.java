package com.company;

import java.awt.geom.Rectangle2D;

public class BurningShip extends FractalGenerator {
    public static final int MAX_ITERATIONS = 2000;


    public String toString() {
        return "BurningShip";
    }

    @Override
    public void getInitialRange(Rectangle2D.Double range) {
        range.x = -2;
        range.y = -2.5;
        range.height = 4;
        range.width = 4;
    }

    @Override
    public int numIterations(double x, double y) {
        int iteraion = 0;
        double x1 = 0;
        double y1 = 0;
        while (x1*x1 + y1*y1 < 4 && iteraion < MAX_ITERATIONS) {
            double temp1 = x1 * x1 - y1 * y1 + x;
            double temp2 = Math.abs(2 * x1 * y1) + y;
            x1 = temp1;
            y1 = temp2;
            iteraion++;
        }
        if (iteraion >= MAX_ITERATIONS)return -1;
        return iteraion;
    }
}
