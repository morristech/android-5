package com.nigorojr.addition;


import android.os.Bundle;
import android.app.Activity;
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
    
    private int incrementedNumber;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
		Button button_increment = (Button)findViewById(R.id.button_increment);
		ButtonClick button_listener = new ButtonClick();
		button_increment.setOnClickListener(button_listener);
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
				incrementedNumber = Integer.parseInt(editText.getText().toString());
				// intent.putExtra(NUMBER, incrementedNumber);
    			incrementedNumber++;
    			editText.setText(incrementedNumber + "");
    			
				// For now, just show a Toast
				Toast.makeText(v.getContext(), "Incremented result is " + incrementedNumber, Toast.LENGTH_LONG);
    		}
    	}
    }
}
