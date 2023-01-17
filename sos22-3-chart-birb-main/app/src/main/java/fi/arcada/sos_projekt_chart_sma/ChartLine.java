package fi.arcada.sos_projekt_chart_sma;

import com.github.mikephil.charting.data.Entry;



import java.util.ArrayList;

import java.util.List;

/*
 *   Samma idé i JavaScript:
 *
 *   const chartLine = {
 *       dataSet = [1,5,3,2,6,3,3,5],
 *       label = "Temperatures",
 *       startOffset = 10,
 *       color = "red",
 *       getLabel = function() {
 *           return this.label
 *       }
 *   }
 * */

public class ChartLine {

    // Attribut
    private ArrayList<Double> dataSet;
    private String label;
    private int color;
    private int startOffset;

    public ChartLine(ArrayList<Double> dataSet, String label, int color, int startOffset) {
        this.dataSet = dataSet;
        this.label = label;
        this.color = color;
        this.startOffset = startOffset;
    }

    /**
     * Returnerar entries att anavändas med MPAndroidChart
     * @return
     */
    public List<Entry> getEntries() {
        List<Entry> entries;

        entries = new ArrayList<Entry>();
        for (int j = 0; j < dataSet.size(); j++) {
            // OBS i = yttre loopen, j = inre loopen
            entries.add(new Entry(j+startOffset, dataSet.get(j).floatValue()));
        }
        return entries;
    }

    // Getters
    public ArrayList<Double> getDataSet() {
        return dataSet;
    }

    public String getLabel() {
        return label;
    }

    public int getColor() {
        return color;
    }

    public int getStartOffset() {
        return startOffset;
    }
}
