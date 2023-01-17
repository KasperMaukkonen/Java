package fi.arcada.sos_projekt_chart_sma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    // deklarera variabler
    LineChart chart;
    EditText textInput;
    TextView currentSettings;
    SharedPreferences sharedPref;
    String currency, datefrom, dateto;
    ArrayList<Integer> showWindows = new ArrayList<>();
    ArrayList<Double> currencyValues = new ArrayList<>();
    //public class SettingsActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chart = (LineChart) findViewById(R.id.chart);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        currentSettings = findViewById(R.id.currentSettings);

        currency = sharedPref.getString("currency", "USD");
        datefrom = sharedPref.getString("datefrom", "2022-01-01");
        dateto = sharedPref.getString("dateto", "2022-02-01");

        currencyValues = getCurrencyValues(currency, datefrom, dateto);
        System.out.println(currencyValues.toString());

        initGraph();
    }


    public void initGraph() {
        currentSettings.setText(String.format("%s | %s - %s", currency, datefrom, dateto));
        ArrayList<ChartLine> dataLines = new ArrayList<>();
        dataLines.add(new ChartLine(currencyValues, currency, Color.BLUE, 0));

        int[] smaColors = {Color.GREEN, Color.RED};

        for (int i = 0; i < showWindows.size(); i++) {
            int W = showWindows.get(i);
            dataLines.add(new ChartLine(Statistics.sma(currencyValues, W), "SMA" + W, smaColors[i], W ));
        }
        createdataLineGraph(dataLines);
    }


    public void createdataLineGraph(ArrayList<ChartLine> dataLines) {

        List<ILineDataSet> dataSeries = new ArrayList<>();

        for (ChartLine dataLine : dataLines) {
            LineDataSet dataSet = new LineDataSet(dataLine.getEntries(), dataLine.getLabel());
            dataSet.setColor(dataLine.getColor());
            dataSet.setDrawCircles(false);
            dataSet.setDrawValues(false);
            dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

            dataSeries.add(dataSet);
        }

        LineData lineData = new LineData(dataSeries);
        chart.getXAxis().setDrawLabels(false);
        chart.setData(lineData);
        chart.invalidate();
    }


    private void toggleSma(Integer window) {
        if (showWindows.contains(window)) {

            showWindows.remove((Integer) window);
        } else {
            showWindows.add(window);
        }
    }

    public void clickHandler(View view) {
        if (view.getId() == R.id.sma10) toggleSma(10);
        if (view.getId() == R.id.sma30) toggleSma(30);

        initGraph();
    }

    public void openSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public ArrayList<Double> getCurrencyValues(String currency, String from, String to) {
        CurrencyApi api = new CurrencyApi();
        ArrayList<Double> currencyData = null;
        String urlString = String.format("https://api.exchangerate.host/timeseries?start_date=%s&end_date=%s&symbols=%s",
            from.trim(),
                to.trim(),
                currency.trim());
                System.out.println(urlString);
        try {
            String jsonData = api.execute(urlString).get();
            if (jsonData != null) {
                currencyData = api.getCurrencyData(jsonData, currency.trim());
                Toast.makeText(getApplicationContext(), String.format("Hamtade %s valutakursvärden från servern",currencyData.size()), Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Hamtade %s valutakursvärden från sevrvern" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return currencyData;
    }
}