package com.example.fetchrewards;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;


public class ItemDetailsActivity extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_item_details);

            TextView itemIdTextView = findViewById(R.id.itemIdTextView);
            TextView listIdTextView = findViewById(R.id.listIdTextView);
            TextView nameTextView = findViewById(R.id.nameTextView);

            Intent intent = getIntent();
            String id = intent.getStringExtra("ID");
            String listId = intent.getStringExtra("ListID");
            String name = intent.getStringExtra("Name");

            itemIdTextView.setText(String.format("Item ID: %s", id));
            listIdTextView.setText(String.format("List ID: %s", listId));
            nameTextView.setText(String.format("Name: %s", name));

            Button backButton = findViewById(R.id.backButton);
            backButton.setOnClickListener(v -> {
                finish(); // Close the current activity and return to the previous one
            });
        }
    }

