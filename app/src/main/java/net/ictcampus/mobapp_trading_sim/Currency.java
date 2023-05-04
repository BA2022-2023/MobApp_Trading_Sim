package net.ictcampus.mobapp_trading_sim;


import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Currency extends AppCompatActivity {
        TextView tv_lat,tv_lon,tv_altitude,tv_speed,tv_sensor,tv_updates,tv_address;
        Switch sw_locationupdates, sw_gps;
        @Override
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_currency);

            tv_lat = findViewById(R.id.tv_lat);
            tv_lon = findViewById(R.id.tv_lon);
            tv_altitude = findViewById(R.id.tv_altitude);
            tv_speed = findViewById(R.id.tv_speed);
            tv_sensor = findViewById(R.id.tv_sensor);
            tv_updates = findViewById(R.id.tv_updates);
            tv_address = findViewById(R.id.tv_address);
            sw_gps = findViewById(R.id.sw_gps);
            sw_locationupdates = findViewById(R.id.sw_locationsupdates);



        }

}
