package com.example.fetchrewards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

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

            itemIdTextView.setText("Item ID: " + id);
            listIdTextView.setText("List ID: " + listId);
            nameTextView.setText("Name: " + name);

            Button backButton = findViewById(R.id.backButton);
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish(); // Close the current activity and return to the previous one
//                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            });
        }



    }

