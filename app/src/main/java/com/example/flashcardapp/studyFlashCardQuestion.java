package com.example.flashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class studyFlashCardQuestion extends AppCompatActivity {
    String value = "";
    String set = "";
    ArrayList<String>questions = new ArrayList<>();
    ArrayList<String>answers = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_flash_card);

        Bundle extras = getIntent().getExtras();
        ArrayList<String> sets = new ArrayList<String>();
        if (extras != null) {
            value = extras.getString("category");
            set = extras.getString("set");
        }
        System.out.println(value);
        TextView category = findViewById(R.id.replaceCategory2);
        category.setText(value);

        try{
            FileInputStream fis = openFileInput("questions.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line = br.readLine();
            while(line != null) {
                if(line.contains(value) && line.contains(set)) {
                    String[] qarray = line.split(",");
                    questions.add(qarray[3]);
                    answers.add(qarray[4]);
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

    }

    public void edit(View view) {
    }

    public void createFlash(View view) {
        Intent intent = new Intent(this, createFlashCard.class);
        Bundle bundle = new Bundle();
        bundle.putString("category", value);
        bundle.putString("set", set);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    public void home(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
