package com.example.flashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SelectSet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_set);


        Bundle extras = getIntent().getExtras();
        String value = "";
        if (extras != null) {
            value = extras.getString("category");
        }
        System.out.println(value);
        TextView category = findViewById(R.id.textView22);
        category.setText(value);
    }

    public void createSet(View view) {
        TextView category = findViewById(R.id.textView22);
        Intent intent = new Intent(this, CreateSet.class);
        Bundle bundle = new Bundle();
        bundle.putString("category", category.getText().toString());

        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void editSet(View view) {
    }

    public void studySet(View view) {
    }

    public void deleteSet(View view) {
    }

    public void home(View view) {
        Intent intent1 = new Intent(this, MainActivity.class);
        startActivity(intent1);
    }
}
