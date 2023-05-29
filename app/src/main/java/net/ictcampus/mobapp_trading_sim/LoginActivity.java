package net.ictcampus.mobapp_trading_sim;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextUsername, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        editTextUsername = findViewById(R.id.username);
        editTextPassword = findViewById(R.id.password);
        Button buttonLogin = findViewById(R.id.loginbtn);
        TextView textViewRegister = findViewById(R.id.registerNow);

        // Set click listener for login button
        buttonLogin.setOnClickListener(v -> {
            // Perform login action
            login();
        });

        // Set click listener for register TextView
        textViewRegister.setOnClickListener(v -> {
            // Open registration activity
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


    }

    @SuppressLint("StaticFieldLeak")
    private void login() {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // Get an instance of the AppDatabase
        AppDatabase appDatabase = AppDatabase.getInstance(this);

        // Get the UserDao from the AppDatabase
        UserDao userDao = appDatabase.userDao();

        // Perform the login check on a background thread using coroutines or AsyncTask
        // For simplicity, we'll use AsyncTask in this example

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                // Retrieve the password from the database based on the entered username
                return userDao.getPasswordByUsernameAndPassword(username, password);
            }

            @Override
            protected void onPostExecute(String storedPassword) {
                if (storedPassword != null) {
                    // Login successful
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                    // Open the main activity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Finish the login activity to prevent going back to it
                } else {
                    // Login failed
                    Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();

    }


}

