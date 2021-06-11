package project;

import java.util.ArrayList;

import static java.lang.Math.PI;

/**
 * <b>
 * Author: Dmytro Pelovych.
 * <p>
 * File: GraphRoot.java.
 * <p>
 * Dedication: this class implements a graph root consisting of R, A, S, φ (min/max), Δφ parameters.
 * Accordingly, it becomes available to compute X/Y co-ordinates and put them into collections so that then
 * GraphPlotter.java handles this data and plots a graph.
 * </b>
 */
public class GraphRoot {
    private final double R;
    private final double A;
    private final double S;
    private final double phiMin;
    private final double phiMax;
    private final double step;
    private final ArrayList<Double> x_list = new ArrayList<>();
    private final ArrayList<Double> y_list = new ArrayList<>();

    /**
     * <b>
     * While creating an instance, the constructor fills collections with data needed to plot a graph.
     * Note: it is necessary to convert φ-angles to radians.
     *
     * @param R       parameter R
     * @param A       parameter A
     * @param S       parameter S (in degrees)
     * @param phi_min parameter φ (min)
     * @param phi_max parameter φ (max)
     * @param step    parameter Δφ
     *                </b>
     */
    public GraphRoot(double R, double A, double S, double phi_min, double phi_max, double step) {
        this.R = R;
        this.A = A;
        this.S = S;
        this.phiMin = phi_min;
        this.phiMax = phi_max;
        this.step = step;
        double S_converted = Math.toRadians(S);
        for (double phi = Math.toRadians(phi_min); phi <= Math.toRadians(phi_max); phi += step) {
            double rho = R + A * Math.cos(2 * PI * phi / S_converted);
            x_list.add(rho * Math.cos(phi));
            y_list.add(rho * Math.sin(phi));
        }
    }

    /**
     * <b>The method accesses the collection of X co-ordinates.</b>
     *
     * @return collection of X co-ordinates
     */
    public ArrayList<Double> getX_list() {
        return this.x_list;
    }

    /**
     * <b>The method accesses the collection of Y co-ordinates.</b>
     *
     * @return collection of Y co-ordinates
     */
    public ArrayList<Double> getY_list() {
        return this.y_list;
    }

    public double[] getParameters() {
        double[] array = new double[6];
        array[0] = R;
        array[1] = A;
        array[2] = S;
        array[3] = phiMin;
        array[4] = phiMax;
        array[5] = step;
        return array;
    }
}