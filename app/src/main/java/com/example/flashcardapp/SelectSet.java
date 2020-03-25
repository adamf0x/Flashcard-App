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
        ArrayList<String> sets = new ArrayList<String>();
        if (extras != null) {
            value = extras.getString("category");
        }
        System.out.println(value);
        TextView category = findViewById(R.id.textView22);
        category.setText(value);

        try{
            FileInputStream fis = openFileInput("sets.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line = br.readLine();
            while(line != null) {
                if(line.contains(value)) {
                    sets.add(line);
                }
                line = br.readLine();
            }
            br.close();
            isr.close();
            fis.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, sets);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) findViewById(R.id.spinner4);
        sItems.setAdapter(adapter);
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
        TextView category = findViewById(R.id.textView22);
        Spinner set = findViewById(R.id.spinner4);
        String selectedSet = set.getSelectedItem().toString();

        Intent intent = new Intent(this, studyFlashCardQuestion.class);
        Bundle bundle = new Bundle();
        bundle.putString("category", category.getText().toString());
        bundle.putString("set", selectedSet);

        intent.putExtras(bundle);
        startActivity(intent);

    }

    public void deleteSet(View view) {
    }

    public void home(View view) {
        Intent intent1 = new Intent(this, MainActivity.class);
        startActivity(intent1);
    }
}
