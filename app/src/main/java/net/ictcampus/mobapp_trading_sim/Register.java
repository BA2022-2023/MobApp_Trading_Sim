package net.ictcampus.mobapp_trading_sim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    TextInputEditText editTextUsername, editTextPassword;
    MaterialButton buttonReg;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editTextUsername = findViewById(R.id.username);
        editTextPassword = findViewById(R.id.password);
        buttonReg = findViewById(R.id.registerbtn);

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username, password;
                username = String.valueOf(editTextUsername.getText());
                password = String.valueOf(editTextPassword.getText());

                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(Register.this, "Enter Username", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Register.this, "Enter Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(SignupActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        generateUser(email, password);

                        if (!task.isSuccessful()) {
                            Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(new Intent(SignupActivity.this, MainActivity.class));
                            finish();
                        }
                    }
                });
    }

//}