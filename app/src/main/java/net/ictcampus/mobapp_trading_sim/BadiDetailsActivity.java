package net.ictcampus.mobapp_trading_sim;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BadiDetailsActivity extends AppCompatActivity {

    private static final String TAG = "BadiInfo";
    private ProgressBar mProgressBar;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badi_details);

        //Intent holen
        Intent intent = getIntent();
        //Zusatzinformationen aus dem Intent holen
        String badiId = intent.getStringExtra("badi");
        String name = intent.getStringExtra("name");

        //Füllen des Textview mit der erhaltenen Information
        TextView textView = findViewById(R.id.badiInfo);
        textView.setText(name);



        //Initialisieren eines ProgressDialogs
        mProgressBar = findViewById(R.id.pBar);
        mProgressBar.setVisibility(View.VISIBLE);

        //laden der badi Infos über private Methdoe
        getBadiTemp("https://www.wiewarm.ch/api/v1/bad.json/" + badiId);


    }


    private void getBadiTemp(String urlParam) {
        //Da dieser Prozess etwas länger dauern könnte, wird er als separater Thread gestartet
        // Executor erlaubt uns, gewisse Dinge asynchron in einem Thread auszuführen
        ExecutorService executor = Executors.newSingleThreadExecutor();
        //Handler wird gebraucht, damit wir den Main-Thread updaten können
        Handler handler = new Handler(Looper.getMainLooper());
        //Executor verlangt ein Runnable, damit wir das run() ausführen können
        executor.execute(() -> {
            //Behälter für die TextDaten aus dem BufferedReader
            StringBuilder msg = new StringBuilder();
            //URL Verbindung vorb.
            HttpURLConnection urlConnection;
            try {
                //wir erhalten URL über Badi Array und erstellen ein URL Obj.
                URL url = new URL(urlParam);
                //und öffnen die Connection
                urlConnection = (HttpURLConnection) url.openConnection();
                //User Input Stream
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                //Damit wir die Stream-Daten lesen können, erstellen wir einenBufferedReader
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                //InputStream lesen bis null übertragen wird
                while ((line = reader.readLine()) != null) {
                    //msg zusammensetzen
                    msg.append(line);
                }

            } catch (IOException ioe) {
                Log.v(TAG, ioe.toString());
            }
            //Das Ganze wird von Background-Thread in unserem Main-Thread "gepostet"
            handler.post(() -> {

                //Als Parameter geben wir die fertige Message als String mit
                parseJson(msg.toString());
            });
        });
    }


    public void parseJson(String jsonString) {
        ArrayAdapter<String> temps = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);//Die Daten, welche von wiewarm gesendet wurden, sind als JSON aufgebaut
        //Sie dir den Aufbau auf wiewarm.ch an
        JSONObject jsonObj;
        try {
            jsonObj = new JSONObject(jsonString);
            //Unterobjekt becken
            JSONObject becken = jsonObj.getJSONObject("becken");
            //Mit Hilfe des Iterators können wir alle Elemente des becken anschauen
            Iterator<String> keys = becken.keys();
            while (keys.hasNext()) {
                //Der Key beinhaltet den Namen des Beckens
                String key = keys.next();
                //Mit Hilfe des Namens können wir das nächste Unterobjekt von JSON lesen
                JSONObject subObj = becken.getJSONObject(key);
                //davon holen wir den Namen und die Temperatur
                String name = subObj.getString("beckenname");
                String temp = subObj.getString("temp");
                //Diese Informationen füllen wir in unseren temps ArrayAdapter
                temps.add(name + ":" + temp + "C");
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        //Die Liste zeigen wir nun in unserer View an
        ListView badiDetails = findViewById(R.id.badiDetails);
        badiDetails.setAdapter(temps);
        //ProgressBar ausblenden
        mProgressBar.setVisibility(View.GONE);
    }
}