package com.nigorojr.addition;


import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// TODO: Implement View.OnClickListener
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button_increment = (Button)findViewById(R.id.button_increment);
        Button button_addition = (Button)findViewById(R.id.button_addition);
        
        ButtonClick button_listener = new ButtonClick();
        
        button_increment.setOnClickListener(button_listener);
        button_addition.setOnClickListener(button_listener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is
        // present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    class ButtonClick implements OnClickListener {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.button_increment) {

                // Increment the input number by 1 and change the text inside the text box
                EditText editText = (EditText)findViewById(R.id.input_a);
                int incrementedNumber;
                if (editText.getText().toString().equals(""))
                	incrementedNumber = 1;
                else {
	                incrementedNumber = Integer.parseInt(editText.getText().toString());
	                incrementedNumber++;
                }
                editText.setText(String.valueOf(incrementedNumber));
                
	            // For now, just show a Toast (there are more than 1 way to do it. And don't forget the .show() at the end!)
                //Toast.makeText(MainActivity.this, "Incremented result is " + incrementedNumber, Toast.LENGTH_LONG).show();
	            Toast.makeText(v.getContext(), "Incremented result is " + incrementedNumber, Toast.LENGTH_SHORT).show();
            }
            else if (v.getId() == R.id.button_addition) {
            	String num_a_String = ((EditText)findViewById(R.id.input_a)).getText().toString();
            	String num_b_String = ((EditText)findViewById(R.id.input_b)).getText().toString();
            	// Don't do anything if either field is empty
            	if (num_a_String.equals("") || num_b_String.equals(""))
            		return;
	        	int num_a = Integer.parseInt(num_a_String);
	        	int num_b = Integer.parseInt(num_b_String);
            	Toast.makeText(v.getContext(), (num_a + num_b) + "", Toast.LENGTH_SHORT).show();
            }
        }
    }
}