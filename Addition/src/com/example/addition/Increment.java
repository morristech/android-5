package com.example.addition;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.widget.TextView;

public class Increment extends Activity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Get intent from activity
        Intent intent = getIntent();
        int a = intent.getIntExtra(MainActivity.NUMBER, 0);
        //SharedPreferences p = getSharedPreferences("numbers", MODE_PRIVATE);
        //int a = p.getInt("num1", 0);
        //Editor e = p.edit();
        // put the incremented value
        //e.putInt("num1", a);
        //e.commit();
        // setContentView(R.layout.activity_increment);
        
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        // Maybe I can do this without making it String
        textView.setText(a);
        
        setContentView(textView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is
        // present.
        getMenuInflater().inflate(R.menu.increment, menu);
        return true;
    }

}
