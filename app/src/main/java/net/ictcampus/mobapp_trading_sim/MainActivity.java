package net.ictcampus.mobapp_trading_sim;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseReference= FirebaseDatabase.getInstance().getReference();

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView username = (TextView) findViewById(R.id.username);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView password = (TextView) findViewById(R.id.password);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) MaterialButton loginbtn = (MaterialButton) findViewById(R.id.loginbtn);

    }
}