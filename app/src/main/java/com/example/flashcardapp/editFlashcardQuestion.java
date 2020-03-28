package com.example.flashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class editFlashcardQuestion extends AppCompatActivity {
    String category;
    String set;
    ArrayList<String> questions;
    ArrayList<String> answers;
    int qnum;
    int ansnum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_flashcard);

        Bundle extras = getIntent().getExtras();
        ArrayList<String> sets = new ArrayList<String>();
        if (extras != null) {
            category = extras.getString("category");
            set = extras.getString("set");
            questions = extras.getStringArrayList("questions");
            answers = extras.getStringArrayList("answers");
            qnum = extras.getInt("qnum");
            ansnum = extras.getInt("ansnum");
        }
        System.out.println(category);
        TextView currCat = findViewById(R.id.replacewithcategory);
        currCat.setText(category);
        TextView currSet = findViewById(R.id.replacewithcategory2);
        currSet.setText(set);
        EditText currQ = findViewById(R.id.editText);
        currQ.setText(questions.get(qnum));
        EditText currAns = findViewById(R.id.editText7);
        currAns.setText(answers.get(ansnum));
    }

    public void deleteCard(View view) {
        deleteUnwantedLine("flashcards.txt", "{" + category + ","+ set + "," + questions.get(qnum) + "," + answers.get(ansnum) +"}");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    public void back(View view) {
        finish();
    }

    public void confirm(View view) {
        editLine("flashcards.txt", "{" + category + ","+ set + "," + questions.get(qnum) + "," + answers.get(ansnum) +"}");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void deleteUnwantedLine(String FILE_NAME, String unwantedLine){
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            String fileContent = "";
            while((text = br.readLine()) != null){
                if(!text.equals(unwantedLine)){
                    fileContent = fileContent + text + System.lineSeparator();
                }
                else{
                    System.out.println("not writing line");
                }
            }
            fos = openFileOutput(FILE_NAME, 0);

            fos.write(fileContent.getBytes());
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void editLine(String FILE_NAME, String editLine){
        TextView newQ = findViewById(R.id.editText);
        TextView newA = findViewById(R.id.editText7);
        String newQuestion = newQ.getText().toString();
        String newAnswer = newA.getText().toString();
        FileInputStream fis = null;
        FileOutputStream fos = null;
        String newCard = "{" + category + "," + set + "," + newQuestion + "," + newAnswer + "}";
        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String text;
            String fileContent = "";
            while((text = br.readLine()) != null){
                System.out.println(text);
                if(text.equals(editLine)){
                    System.out.println(editLine);
                    fileContent = fileContent + newCard + System.lineSeparator();
                } else {
                    fileContent = fileContent + text + System.lineSeparator();
                }
            }
            fos = openFileOutput(FILE_NAME, 0);

            fos.write(fileContent.getBytes());
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
