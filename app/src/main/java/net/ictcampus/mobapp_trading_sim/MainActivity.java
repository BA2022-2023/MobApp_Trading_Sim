package net.ictcampus.mobapp_trading_sim;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView mCountryTextView;
    private TextView mCoordinatesTextView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCountryTextView = findViewById(R.id.country_text_view);
        mCoordinatesTextView = findViewById(R.id.coordinates_text_view);

// Berechtigung überprüfen und Anforderung initiieren, falls notwendig
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);
            return;
        }

// GPS-Koordinaten erhalten und Land bestimmen
        Location location = getLocation();
        if (location != null) {
            String country = getCountryFromLocation(location);
            mCountryTextView.setText(country);
            String coordinates = location.getLatitude() + ", " + location.getLongitude();
            mCoordinatesTextView.setText(coordinates);
        }
    }



    // GPS-Koordinaten von LocationManager abrufen
    private Location getLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

    // Land aus GPS-Koordinaten mit Geocoder abrufen
    private String getCountryFromLocation(Location location) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

        if (!addresses.isEmpty()) {
            String country = addresses.get(0).getCountryName();
            return country;
        } else {
            return "";
        }
    }

    // Berechtigungsanforderung verarbeiten
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
// Wenn die Anforderung abgebrochen wurde, wird das Ergebnis-Array leer sein.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
// Berechtigung erteilt, GPS-Koordinaten abrufen und Land bestimmen
                    Location location = getLocation();
                    if (location != null) {
                        String country = getCountryFromLocation(location);
                        mCountryTextView.setText(country);
                        String coordinates = location.getLatitude() + ", " + location.getLongitude();
                        mCoordinatesTextView.setText(coordinates);
                    }
                } else {
// Berechtigung verweigert, Benutzer benachrichtigen und App schließen
                    Log.d("MainActivity", "Permission denied");
                    mCountryTextView.setText("Permission denied");
                    mCoordinatesTextView.setText("Permission denied");
                    finish();
                }
                return;
            }
        }
    }
}