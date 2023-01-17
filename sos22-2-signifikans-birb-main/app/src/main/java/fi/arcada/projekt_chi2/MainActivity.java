package fi.arcada.projekt_chi2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Deklarera 4 Button-objekt
    Button btn1, btn2, btn3, btn4;

    // Deklarera 4 heltalsvariabler för knapparnas värden
    int val1, val2, val3, val4;

    // Deklerera text fälten för raderna och kolumnerna
    TextView col1, col2, col3, col4, row1, row2, row3, text, text2, text3, text4;
    String textSvar = "sannolikhet inte oberoende och kan betrakts som signifikant.";

    SharedPreferences sharedPref;
    SharedPreferences.Editor prefEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Variabler för rader och kolumner
        col1 = findViewById(R.id.textViewCol1);
        col2 = findViewById(R.id.textViewCol2);
        col3 = findViewById(R.id.textViewCol3);
        col4 = findViewById(R.id.textViewCol4);
        row1 = findViewById(R.id.textViewRow1);
        row2 = findViewById(R.id.textViewRow2);
        row3 = findViewById(R.id.textViewRow3);

        //Variabler för text fält
        text = findViewById(R.id.textView);
        text2 = findViewById(R.id.textView2);
        text3 = findViewById(R.id.textView3);
        text4 = findViewById(R.id.textView4);

        //Initsierar shared prefs
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        prefEditor = sharedPref.edit();
        prefEditor.apply();

        // Koppla samman Button-objekten med knapparna i layouten
        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);
        btn4 = findViewById(R.id.button4);

        //Editering för radern och kolumner via settings activity
        col1.setText(String.format(sharedPref.getString("kolumn1", "Barn")));
        col2.setText(String.format(sharedPref.getString("kolumn2", "Vuxna")));

        row1.setText(String.format(sharedPref.getString("rad1", "Spelar OSRS")));
        row2.setText(String.format(sharedPref.getString("rad2", "Spelar inte OSRS")));
        row3.setText(String.format(sharedPref.getString("rad3", "Spelar")));

    }

     /*
         ***  Klickhanterare för knapparna  ***
     */

    //Settings knapp
    public void openSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }


    public void buttonClick(View view) {

        // Skapa ett Button-objekt genom att type-casta (byta datatyp)
        // på det View-objekt som kommer med knapptrycket
        Button btn = (Button) view;

        // Kontrollera vilken knapp som klickats, öka värde på rätt vaiabel
        if (view.getId() == R.id.button1) val1++;
        if (view.getId() == R.id.button2) val2++;
        if (view.getId() == R.id.button3) val3++;
        if (view.getId() == R.id.button4) val4++;

        // Slutligen, kör metoden som ska räkna ut allt!
        calculate();
    }

    /**
     * Metod som uppdaterar layouten och räknar ut själva analysen.
     */
    public void calculate() {

        // Uppdatera knapparna med de nuvarande värdena
        btn1.setText(String.valueOf(val1));
        btn2.setText(String.valueOf(val2));
        btn3.setText(String.valueOf(val3));
        btn4.setText(String.valueOf(val4));



        // Mata in värdena i Chi-2-uträkningen och ta emot resultatet
        // i en Double-variabel
        double chi2 = Significance.chiSquared(val1, val2, val3, val4);

        // Mata in chi2-resultatet i getP() och ta emot p-värdet
        double pValue = Significance.getP(chi2);

        double signifikans = Significance.getSig(pValue);

        double procentage1 = Significance.procentage(val1, val3);

        double procentage2 = Significance.procentage(val2, val4);

        //Display results
        col3.setText(String.format("%6.2f%%.%n", procentage1));

        col4.setText(String.format("%6.2f%%.%n", procentage2));

        text.setText(String.format("Chi-2 resulat: " + "%.2f", chi2));

        text2.setText(String.format("Signifikansnivå: " + "%.2f", pValue));

        text3.setText(String.format("P-värde: " + "%.3f", pValue));

        text4.setText(String.format("\nResultatet är med" + "%6.2f%%.%n%s", signifikans, textSvar));

        /**
         *  - Visa chi2 och pValue åt användaren på ett bra och tydligt sätt!
         *
         *  - Visa procentuella andelen jakande svar inom de olika grupperna.
         *    T.ex. (val1 / (val1+val3) * 100) och (val2 / (val2+val4) * 100
         *
         *  - Analysera signifikansen genom att jämföra p-värdet
         *    med signifikansnivån, visa reultatet åt användaren
         *
         */

    }

   public void reset(View view)
    {
        // do your work Here
        Intent intent= new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}