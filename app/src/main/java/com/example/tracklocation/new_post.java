package com.example.tracklocation;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class new_post extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        EditText editTextPostContent = findViewById(R.id.editTextPostContent);
        Button buttonSubmitPost = findViewById(R.id.buttonSubmitPost);

        if (editTextPostContent != null && buttonSubmitPost != null) {
            // Your existing code for setting click listener
        } else {
            // Log or handle the case where views are not found
        }

        buttonSubmitPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click event of the submit post button
                String postContent = editTextPostContent.getText().toString();

                if (!postContent.isEmpty()) {
                    // TODO: Add code to handle the submission logic (e.g., send the post to a server or store it locally)

                    // For demonstration purposes, show a toast message
                    Toast.makeText(new_post.this, "Post submitted: " + postContent, Toast.LENGTH_SHORT).show();

                    // Finish the activity if the post is submitted successfully
                    finish();
                } else {
                    Toast.makeText(new_post.this, "Please enter your post", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}