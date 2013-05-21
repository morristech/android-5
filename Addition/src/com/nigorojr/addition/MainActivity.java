package com.nigorojr.addition;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

// TODO: Implement View.OnClickListener
public class MainActivity extends Activity {
    
    public static final String NUMBER = "com.nigorojr.addition.NUMBER";
    
    public void increment(View view) {
        Intent intent = new Intent(this, Increment.class);
		EditText editText = (EditText)findViewById(R.id.input_a);
		int a = Integer.parseInt(editText.getText().toString());
		a++;
		intent.putExtra(NUMBER, a);
		
		// For now, just show a Toast
		Toast.makeText(this, "Incremented result is " + a, Toast.LENGTH_SHORT);
		
		startActivity(intent);
    }
    
    public void addition(View view) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is
        // present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
