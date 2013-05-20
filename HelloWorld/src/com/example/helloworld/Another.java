package com.example.helloworld;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Another extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		
		message += "\nAdded some string in the Another class.";
		
		TextView textView = new TextView(this);
		textView.setTextSize(20);
		textView.setText(message);
		
		setContentView(textView);
	}
}
