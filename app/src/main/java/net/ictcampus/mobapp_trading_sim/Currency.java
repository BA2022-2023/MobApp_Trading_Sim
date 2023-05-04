package net.ictcampus.mobapp_trading_sim;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;


public class Currency extends AppCompatActivity {
    ArrayAdapter badiListe;
    private final static String AARBERG = "Schwimmbad Aarberg (BE)";
    private final static String ADELBODEN = "Schwimmbad Gruebi Adelboden (BE)";
    private final static String GRENCHEN = "Schwimmbad Grenchen (SO)";
    private final static String ZHSEE = "Zürichsee (ZH)";

    private void addBadisToList() {
        ListView badis = findViewById(R.id.badiListe);
        badiListe = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        for (String s : Arrays.asList(AARBERG, ADELBODEN, GRENCHEN, ZHSEE)) {
            badiListe.add(s);
        }
        badis.setAdapter(badiListe);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);



        addBadisToList();


        //KlickListener in anonymer Klasse an Adapter hängen
        AdapterView.OnItemClickListener mListClickHandler = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), BadiDetailsActivity.class);
                String selected = parent.getItemAtPosition(position).toString();
//Kleine Infobox, dass der Klick empfangen wurde
                Toast.makeText(getApplicationContext(), selected, Toast.LENGTH_SHORT).show();
                //Zusatzinformationen an den intent (andere Activity), welche Badi ID
                switch (selected) {
                    case AARBERG:
                        intent.putExtra("badi", "71");
                        break;
                    case ADELBODEN:
                        intent.putExtra("badi", "27");
                        break;
                    case GRENCHEN:
                        intent.putExtra("badi", "174");
                        break;
                    case ZHSEE:
                        intent.putExtra("badi", "55");
                        break;
                }
                intent.putExtra("name", selected);
                startActivity(intent);

            }
        };
        //Listener an ListView hängen
        ListView badis = findViewById(R.id.badiListe);
        badis.setOnItemClickListener(mListClickHandler);

    }


}
