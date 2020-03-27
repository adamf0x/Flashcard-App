package com.example.flashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class studyFlashCardQuestion extends AppCompatActivity {
    int qnum = 0;
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
            FileInputStream fis = openFileInput("flashcards.txt");
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
        TextView questionText = findViewById(R.id.textView26);
        if(questions.size() > 0) {
            questionText.setText(questions.get(qnum));
        }
        else {
            questionText.setText("Create some questions for this set to begin studying!");
            Toast toast = Toast.makeText(getApplicationContext(), "please create some questions for this set using the create new card button!", Toast.LENGTH_SHORT);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();
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

    public void nextQ(View view) {
        TextView questionText = findViewById(R.id.textView26);
        qnum = (qnum+1)%questions.size();
        questionText.setText(questions.get(qnum));
    }
}
