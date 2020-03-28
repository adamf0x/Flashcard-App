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
    int ansnum = 0;
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
                    questions.add(qarray[qarray.length-2]);
                    answers.add(qarray[qarray.length-1].replace("}", ""));
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
        if(questions.size() >0) {
            Intent intent = new Intent(this, editFlashcardQuestion.class);
            Bundle bundle = new Bundle();
            bundle.putString("category", value);
            bundle.putString("set", set);
            bundle.putInt("qnum", qnum);
            bundle.putInt("ansnum", ansnum);
            bundle.putStringArrayList("questions", questions);
            bundle.putStringArrayList("answers", answers);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(), "create some flashcards first!", Toast.LENGTH_SHORT);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();
        }
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
        if(questions.size() > 0) {
            TextView questionText = findViewById(R.id.textView26);
            qnum = (qnum + 1) % questions.size();
            questionText.setText(questions.get(qnum));
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(), "create some more flashcards first!", Toast.LENGTH_SHORT);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void flipCard(View view) {
        if(answers.size() > 0) {
            TextView questionText = findViewById(R.id.textView26);
            if (questionText.getText().equals(questions.get(qnum))) {
                TextView answerText = findViewById(R.id.textView26);
                ansnum = qnum;
                answerText.setText(answers.get(ansnum));
            } else {
                questionText.setText(questions.get(qnum));
            }
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(), "create some flashcards first!", Toast.LENGTH_SHORT);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
