package com.nigorojr.addition;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends Activity implements OnClickListener {
	
	private int passed_int;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		
		// Get the passed String
		Intent intent = getIntent();
		String passed = intent.getStringExtra("PASSED_STRING");
		
		// Only set the String if the key is correct, in other words, the "button_for_result" button was clicked
		if (passed != null) {
			// Show what has been passed from the previous activity
			TextView tv = (TextView)findViewById(R.id.text_addition);
			tv.setText("Result of Addition: " + passed);
		}
		else
			// Probably best if there is a check to see if the getStringExtra returned a non-null String
			passed_int = Integer.parseInt(intent.getStringExtra("FOR_RESULT"));
			
		// Add button that allows the user to close this Activity
		Button button_close = (Button)findViewById(R.id.button_close);
		button_close.setOnClickListener(this);
		
		Button button_for_result = (Button)findViewById(R.id.button_result);
		button_for_result.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// Close Activity when the close button is clicked
		if (v.getId() == R.id.button_close)
			finish();
		// Close Activity AFTER setting result
		else if (v.getId() == R.id.button_result) {
			Intent intent = getIntent();
			EditText input_result = (EditText)findViewById(R.id.input_result);
			String input = input_result.getText().toString();
			// Don't do anything if the input field is empty
			if (input.equals(""))
				return;
			
			// Put an Extra to the intent with its value being the sum of the input field num and passed integer + 100
			intent.putExtra("ANOTHER_NUMBER", Integer.parseInt(input) + passed_int + 100);
			// Set the result
			setResult(RESULT_OK, intent);
			finish();
		}
	}
}
