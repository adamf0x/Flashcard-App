package com.example.flashcardapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class deleteSet extends AppCompatActivity {
    String set;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Works for deleting from sets.txt, haven't tested for deleting flashcards.txt yet
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_set);
        set = getIntent().getStringExtra("set");
        Button yes = (Button) findViewById(R.id.yes);
        TextView text = (TextView) findViewById(R.id.deleteingSet);
        text.setText("Deleting set: " + set + "\nContinue?");
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUnwantedLine("sets.txt", set);
                deleteUnwantedLine("flashcards.txt", "," + set + ",");
                Intent intent = new Intent(deleteSet.this, SelectSet.class);
                startActivity(intent);
            }
        });

        Button no = (Button) findViewById(R.id.no);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    void deleteUnwantedLine(String FILE_NAME, String unwantedLine){
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
                if(!text.contains(unwantedLine)){
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
