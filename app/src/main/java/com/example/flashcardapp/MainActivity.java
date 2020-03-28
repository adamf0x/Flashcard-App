package com.example.flashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> categories = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createFiles(); //Creates .txt files if they don't exist, prevents crashing via filenotfound
        try{
            FileInputStream fis = openFileInput("categories.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line = br.readLine();
            while(line != null) {
                if(line.length()>0) {
                    categories.add(line);
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
                this, android.R.layout.simple_spinner_item, categories);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) findViewById(R.id.category);
        sItems.setAdapter(adapter);
    }

    public void viewCategory(View view) {
        Spinner cat = findViewById(R.id.category);
        String selectedCat = cat.getSelectedItem().toString();

        Intent intent = new Intent(this, SelectSet.class);
        Bundle bundle = new Bundle();

        bundle.putString("category", selectedCat);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void editCategory(View view) {
        Spinner cat = findViewById(R.id.category);
        String selectedCat = cat.getSelectedItem().toString();
        Intent intent = new Intent(this,editCategory.class);
        intent.putExtra("category", selectedCat);
        startActivity(intent);
    }

    public void createCategory(View view) {
        Intent intent = new Intent(this,createCategory.class);
        startActivity(intent);

    }
    private void createFiles(){
        try {
            FileInputStream in = openFileInput("categories.txt");
        } catch (FileNotFoundException e) {
            File file = new File("categories.txt");
        }
        try {
            FileInputStream in = openFileInput("sets.txt");
        } catch (FileNotFoundException e) {
            File file = new File("sets.txt");
        }
        try {
            FileInputStream in = openFileInput("flashcards.txt");
        } catch (FileNotFoundException e) {
            File file = new File("flashcards.txt");
        }
    }
}
