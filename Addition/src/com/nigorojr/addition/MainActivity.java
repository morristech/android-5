package com.nigorojr.addition;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	public static final int REQUEST_ANOTHER_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button_increment = (Button)findViewById(R.id.button_increment);
        Button button_addition = (Button)findViewById(R.id.button_addition);
        Button button_for_result = (Button)findViewById(R.id.button_for_result);
        
        ButtonClick button_listener = new ButtonClick();
        
        button_increment.setOnClickListener(button_listener);
        button_addition.setOnClickListener(button_listener);
        button_for_result.setOnClickListener(button_listener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is
        // present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    /**
     * This method is only used when the button "button_result" is clicked.
     * Sets the TextView in this View to the number that was returned from the other Activity.
     * Sets to -1 when there is an error.
     * @param requestCode The request code that was used when starting the other Activity.
     * @param resultCode The result code that was set in the other activity after setting the result.
     * @param data The Intent containing the result from the other activity.
     */
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	TextView tv = (TextView)findViewById(R.id.result_textView);
    	if (requestCode == REQUEST_ANOTHER_ACTIVITY) {
    		if (resultCode == RESULT_OK) {
    			// Either works
    			// int a = data.getIntExtra("ANOTHER_NUMBER", -1);
	    		// tv.setText(String.valueOf(a));
	    		tv.setText("100 + upper number + number from the other Activity\n= " + (data.getIntExtra("ANOTHER_NUMBER", -1)));
    		}
    		else
    			Toast.makeText(MainActivity.this, "Canceled", Toast.LENGTH_SHORT).show();
    	}
    }
    
    class ButtonClick implements OnClickListener {
        @Override
        public void onClick(View v) {
        	// Get the number in the first input field
            EditText editText = (EditText)findViewById(R.id.input_a);
            int num_a;
            if (editText.getText().toString().equals(""))
            	num_a = 1;
            else
                num_a = Integer.parseInt(editText.getText().toString());
            
            
            if (v.getId() == R.id.button_increment) {

                // Increment the input number by 1 and change the text inside the text box
                num_a++;
                editText.setText(String.valueOf(num_a));
                
	            // For now, just show a Toast (there are more than 1 way to do it. And don't forget the .show() at the end!)
                //Toast.makeText(MainActivity.this, "Incremented result is " + incrementedNumber, Toast.LENGTH_LONG).show();
	            Toast.makeText(v.getContext(), "Incremented result is " + num_a, Toast.LENGTH_SHORT).show();
            }
            else if (v.getId() == R.id.button_addition) {
            	String num_b_String = ((EditText)findViewById(R.id.input_b)).getText().toString();
            	// Don't do anything if the field is empty
            	if (num_b_String.equals(""))
            		return;
	        	int num_b = Integer.parseInt(num_b_String);
            	Toast.makeText(v.getContext(), (num_a + num_b) + "", Toast.LENGTH_SHORT).show();
            	
            	Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            	intent.putExtra("PASSED_STRING", String.valueOf(num_a + num_b));
            	
            	startActivity(intent);
            }
            
            else if (v.getId() == R.id.button_for_result) {
            	Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            	intent.putExtra("FOR_RESULT", String.valueOf(num_a));
            	startActivityForResult(intent, REQUEST_ANOTHER_ACTIVITY);
            }
        }
    }
}