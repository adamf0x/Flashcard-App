package com.example.flashcardapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class SelectSet extends AppCompatActivity {
    ArrayList<String> sets = new ArrayList<String>();
    String category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_set);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            category = extras.getString("category");
        }
        TextView currCategory = findViewById(R.id.textView22);
        currCategory.setText(category);

        try{
            FileInputStream fis = openFileInput("sets.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line = br.readLine();
            while(line != null) {
                if(line.contains(category)) {
                    String[] catSets = line.split(",");
                    sets.add(catSets[1]);
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


    public void studySet(View view) {
        if(sets.size() > 0) {
            TextView category = findViewById(R.id.textView22);
            Spinner set = findViewById(R.id.spinner4);
            String selectedSet = set.getSelectedItem().toString();

            Intent intent = new Intent(this, studyFlashCardQuestion.class);
            Bundle bundle = new Bundle();
            bundle.putString("category", category.getText().toString());
            bundle.putString("set", selectedSet);

            intent.putExtras(bundle);
            startActivity(intent);
        }else{
            Toast toast = Toast.makeText(getApplicationContext(), "Please create some sets for this category by clicking the create button", Toast.LENGTH_LONG);
            toast.show();
        }

    }

    public void deleteSet(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Delete Set?");
        builder.setMessage("Are you sure you want to delete this flashcard set?");
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        confirmedDelete();
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void home(View view) {
        Intent intent1 = new Intent(this, MainActivity.class);
        startActivity(intent1);
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
                if(!text.equals(unwantedLine)){
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
    void confirmedDelete(){
        Spinner set = findViewById(R.id.spinner4);
        String selectedSet = set.getSelectedItem().toString();
        deleteUnwantedLine("sets.txt", category +","+selectedSet);
        deleteUnwantedLine("flashcards.txt", "," + selectedSet + ",");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
