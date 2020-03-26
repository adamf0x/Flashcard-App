package com.example.flashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.util.ArrayList;

public class createFlashCard extends AppCompatActivity {
    String value = "";
    String set = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_flash_card);

        Bundle extras = getIntent().getExtras();
        ArrayList<String> sets = new ArrayList<String>();
        if (extras != null) {
            value = extras.getString("category");
            set = extras.getString("set");
        }
        System.out.println(value);
        TextView category = findViewById(R.id.categoryReplace);
        category.setText(value);
        TextView currSet = findViewById(R.id.categoryReplace2);
        currSet.setText(set);
    }

    public void makeAnother(View view) {
        TextView question = findViewById(R.id.editText4);
        CharSequence q = question.getText();
        TextView answer = findViewById(R.id.editText5);
        CharSequence ans = answer.getText();

        Bundle extras = getIntent().getExtras();
        ArrayList<String> sets = new ArrayList<String>();
        if (extras != null) {
            value = extras.getString("category");
            set = extras.getString("set");
        }

        String filename = "flashcards.txt";
        String fileContents= "{" + value + "," + set + "," + q + "," + ans + "}\n";
        FileOutputStream outputStream; //allow a file to be opened for writing
        try {
            outputStream= openFileOutput(filename, Context.MODE_APPEND);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Toast toast = Toast.makeText(getApplicationContext(), "Flashcard created.", Toast.LENGTH_SHORT);
        toast.show();
        question.setText("");
        answer.setText("");
    }

    public void back(View view) {
        Intent intent = new Intent(this, studyFlashCardQuestion.class);
        Bundle bundle = new Bundle();
        bundle.putString("category", value);
        bundle.putString("set", set);
        intent.putExtras(bundle);
        startActivity(intent);

    }
}
