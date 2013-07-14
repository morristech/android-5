package com.nigorojr.sharedpreferencessample;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.CheckBox;
import android.view.View;
import android.content.SharedPreferences;

public class MainActivity extends Activity implements View.OnClickListener {
    
    TextView tv = null;
    CheckBox cb = null;
    Spinner spinner = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView)findViewById(R.id.title);
        cb = (CheckBox)findViewById(R.id.checkbox);
        spinner = (Spinner)findViewById(R.id.spinner);
        findViewById(R.id.btn_get).setOnClickListener(this);
        findViewById(R.id.btn_save).setOnClickListener(this);
        findViewById(R.id.btn_remove).setOnClickListener(this);
    }
    
    public void get() {
        SharedPreferences sp = getSharedPreferences("P_FILE", MODE_PRIVATE);
        String title = sp.getString("Title", "Default value");
        boolean isMute = sp.getBoolean("Mute", false);
        int color = sp.getInt("Color", 0);
        tv.setText(title);
        cb.setChecked(isMute);
        spinner.setSelection(color);
    }
    
    public void save() {
        SharedPreferences sp = getSharedPreferences("P_FILE", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("Title", tv.getText().toString());
        editor.putBoolean("Mute", cb.isChecked());
        editor.putInt("Color", spinner.getSelectedItemPosition());
        
        editor.commit();
    }
    
    public void remove() {
        SharedPreferences sp = getSharedPreferences("P_FILE", MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.clear();
        e.commit();
    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get:
                get();
                break;
            case R.id.btn_save:
                save();
                break;
            case R.id.btn_remove:
                remove();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
