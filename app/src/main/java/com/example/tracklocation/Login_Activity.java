package com.example.tracklocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login_Activity extends AppCompatActivity {

    private EditText editEmail, editPassword;
    private Button btnLogin,btnRegister;
    private FirebaseAuth mAuth; // Declare FirebaseAuth instance

    Animation scaleUp, scaleDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        editEmail = findViewById(R.id.email);
        editPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_Activity.this, Register_Activity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(v -> {
            String email = editEmail.getText().toString();
            String password = editPassword.getText().toString();
            if (!email.isEmpty() && !password.isEmpty()) {
                login(email, password);
            } else {
                Toast.makeText(getApplicationContext(), "Sila Isi Semua Data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void login(String email, String password) {
        // Sign in with email and password
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            reload();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login_Activity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void reload() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish(); // Finish this activity so user can't go back to login screen after successful login
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            reload();
        }
    }
}
