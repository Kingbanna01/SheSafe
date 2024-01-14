package com.example.tracklocation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CommunityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        Button postButton = findViewById(R.id.ButtonPost);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click event of the post button
                // For simplicity, we'll just show a toast message for demonstration purposes
                //Toast.makeText(MainActivity.this, "Post submitted", Toast.LENGTH_SHORT).show();

                // TODO: Add code to handle the posting logic (e.g., sending the post to a server or storing it locally)

                // Navigate to the NewPostActivity
                Intent intent = new Intent(CommunityActivity.this, new_post.class);
                startActivity(intent);
            }
        });
    }
}