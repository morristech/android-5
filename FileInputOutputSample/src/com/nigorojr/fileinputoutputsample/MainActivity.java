package com.nigorojr.fileinputoutputsample;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.EditText;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.IOException;

public class MainActivity extends Activity implements View.OnClickListener {
    
    public static final int BUTTON_SAVE = 0;
    public static final int BUTTON_LOAD = 1;
    
    public void action() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        // 0: Save, 1: Load
        int buttonIs = Integer.valueOf(sp.getString("button_works_as", "0"));
        switch (buttonIs) {
            case BUTTON_SAVE:
                save();
                break;
            case BUTTON_LOAD:
                load();
                break;
        }
    }
    
    public void save() {
        try {
            FileOutputStream out = this.openFileOutput("testTest.txt", MODE_PRIVATE);
            PrintWriter writer = new PrintWriter(out);
            EditText et = (EditText)findViewById(R.id.editText);
            String text = et.getText().toString();
            writer.write(text);

            writer.flush();
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void load() {
        try {
            FileInputStream in = openFileInput("testTest.txt");
            Scanner input = new Scanner(in);
            String content = "";
            while (input.hasNext())
                content += input.nextLine();
            
            EditText et = (EditText)findViewById(R.id.editText);
            et.setText(content);
            
            Toast.makeText(getApplicationContext(), "Lock & Loaded!!", Toast.LENGTH_SHORT).show();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        findViewById(R.id.button).setOnClickListener(this);
        prep();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivityForResult(new Intent(this, MainPreferenceActivity.class), 0);
                break;
        }
        return true;
    }
    
    @Override
    public void onActivityResult(int request, int result, Intent data) {
        if (request != 0)
            return;
        prep();
    }
    
    public void prep() {
        Button b = (Button)findViewById(R.id.button);
        if (Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                .getString("button_works_as", "0")) == BUTTON_SAVE)
            b.setText("Save");
        else
            b.setText("Load");
    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                action();
                break;
        }
    }

}
