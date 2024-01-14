package com.example.tracklocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Register_Activity extends AppCompatActivity {
    private EditText editName, editEmail, editPassword, editPasswordConf;
    private Button btnRegister;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editName = findViewById(R.id.name);
        editEmail = findViewById(R.id.email);
        editPassword = findViewById(R.id.password);
        editPasswordConf = findViewById(R.id.password_conf);
        btnRegister = findViewById(R.id.btn_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(Register_Activity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setCancelable(false);

        btnRegister.setOnClickListener(v -> {
            if (editName.getText().length() > 0 && editEmail.getText().length() > 0 && editPassword.getText().length() > 0 && editPasswordConf.getText().length() > 0) {
                if (editPassword.getText().toString().equals(editPasswordConf.getText().toString())) {
                    register(editName.getText().toString(), editEmail.getText().toString(), editPassword.getText().toString());
                } else {
                    Toast.makeText(getApplicationContext(), "Your passwords do not match", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Please fill in all the fields", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void register(String name, String email, String password) {
        if (isFinishing()) {
            return; // Activity is finishing, do not proceed
        }

        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (isFinishing()) {
                    progressDialog.dismiss(); // Dismiss the dialog if the activity is finishing
                    return; // Activity is finishing, do not proceed
                }

                if (task.isSuccessful() && task.getResult() != null) {
                    FirebaseUser firebaseUser = task.getResult().getUser();
                    if (firebaseUser != null) {
                        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .build();
                        firebaseUser.updateProfile(request).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressDialog.dismiss(); // Dismiss the dialog when the task is complete
                                if (task.isSuccessful()) {
                                    Toast.makeText(Register_Activity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Register_Activity.this, MainActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(Register_Activity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        progressDialog.dismiss(); // Dismiss the dialog if firebaseUser is null
                        Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressDialog.dismiss(); // Dismiss the dialog if the task is not successful
                    Toast.makeText(getApplicationContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
