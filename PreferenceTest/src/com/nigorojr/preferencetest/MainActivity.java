package com.nigorojr.preferencetest;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.openPref).setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                // startActivity(new android.content.Intent(MainActivity.this, Preference.class));
                startActivityForResult(new android.content.Intent(MainActivity.this, Preference.class), 0);
                // ((android.widget.TextView)findViewById(R.id.editText)).setText(android.preference.PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getString("et", "Default"));
                // ((android.widget.TextView)findViewById(R.id.checkBox)).setText(android.preference.PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getBoolean("cb", false) + "");
                // ((android.widget.TextView)findViewById(R.id.spinner)).setText(android.preference.PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getString("list", "Default"));
            }
        });
    }
    
    @Override
    public void onActivityResult(int request, int result, android.content.Intent intent) {
        // Have to use the default SharedPreferences?
        ((android.widget.TextView)findViewById(R.id.editText)).setText(android.preference.PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getString("et", "Default"));
        ((android.widget.TextView)findViewById(R.id.checkBox)).setText(android.preference.PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getBoolean("cb", false) + "");
        ((android.widget.TextView)findViewById(R.id.spinner)).setText(android.preference.PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getString("list", "Default"));
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
