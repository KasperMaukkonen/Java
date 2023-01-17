package fi.arcada.sos_projekt_chart_sma;

import java.util.ArrayList;

public class Statistics {

    public static ArrayList<Double> sma(ArrayList<Double> dataset, int window) {
        ArrayList<Double> ma = new ArrayList<>();

        for (int i = window-1; i < dataset.size() - 1; i++) {
            double sum = 0.0;

            for (int j = 0; j < window; j++) {
                sum += dataset.get(i - j);
            }
            ma.add(sum / window);
        }
        return ma;
    }
}