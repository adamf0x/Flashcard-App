package com.example.flashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CreateSet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_set);

        Bundle extras = getIntent().getExtras();
        String value = "";
        if (extras != null) {
            value = extras.getString("category");
        }
        TextView cat = findViewById(R.id.textView23);
        cat.setText(value);

    }

    public void done(View view) {
        TextView set = findViewById(R.id.editText3);
        CharSequence setDesc = set.getText();

        Bundle extras = getIntent().getExtras();
        String value = "";
        if (extras != null) {
            value = extras.getString("category");
        }

        String filename = "sets.txt";
        String fileContents= value + "," + setDesc + "\n";
        FileOutputStream outputStream; //allow a file to be opened for writing
        try {
            outputStream= openFileOutput(filename, Context.MODE_APPEND);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(this,SelectSet.class);
        Bundle bundle = new Bundle();
        bundle.putString("category", value);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void home(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
