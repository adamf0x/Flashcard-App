package com.example.flashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.FileOutputStream;

public class createCategory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_category);
    }


    public void createCategory(View view) {
        TextView catName = findViewById(R.id.editText2);
        CharSequence category = catName.getText();
        category = category.toString();

        String filename = "categories.txt";
        String fileContents= "\n" + category; //NOTE: I changed this from (category + "\n") to fix the bug
        FileOutputStream outputStream; //allow a file to be opened for writing
        try {
            outputStream= openFileOutput(filename, Context.MODE_APPEND);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
