package com.example.flashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class editCategory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);

        Button rename = (Button) findViewById(R.id.renameButton);
        rename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView catName = findViewById(R.id.editcat);
                CharSequence category = catName.getText();
                String newCat = category.toString();
                String oldCat = getIntent().getStringExtra("category");

                modifyFile("categories.txt", oldCat, newCat);
                modifyFile("flashcards.txt", "{" + oldCat, "{" + newCat);
                modifyFile("sets.txt", oldCat + ",", newCat + ",");
                Intent intent = new Intent(editCategory.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button delete = (Button) findViewById(R.id.deleteButton);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView catName = findViewById(R.id.editcat);
                CharSequence category = catName.getText();
                String newCat = category.toString();
                String oldCat = getIntent().getStringExtra("category");

                modifyFile("categories.txt", oldCat, "");
                removeEmptyLines("categories.txt");
                modifyFile("flashcards.txt", "{" + oldCat, "{");
                removeEmptyLines("flashcards.txt");
                modifyFile("sets.txt", oldCat + ",", ",");
                removeEmptyLines("sets.txt");
                Intent intent = new Intent(editCategory.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

     void modifyFile(String FILE_NAME, String oldString, String newString){
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            String oldContent = "";
            while((text = br.readLine()) != null){
                oldContent = oldContent + text + System.lineSeparator();
            }
            String newContent = oldContent.replaceAll(oldString, newString);
            fos = openFileOutput(FILE_NAME, 0);

            fos.write(newContent.getBytes());
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

    void removeEmptyLines (String FILE_NAME){
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            String newContent = "";
            while((text = br.readLine()) != null){
                if(text.length() > 0){
                    newContent = newContent + text;
                }
            }
            fos = openFileOutput(FILE_NAME, 0);

            fos.write(newContent.getBytes());
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
