package com.nigorojr.dogage;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button b = (Button)findViewById(R.id.button_calculate);
		final Context thisContext = this;
		
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int input = Integer.parseInt(((EditText)(findViewById(R.id.text_year))).getText().toString());
				int humanAge;
				switch (input) {
					case 1:
						humanAge = 17;
						break;
					case 2:
						humanAge = 24;
						break;
					default:
						humanAge = 24 + (input - 2) * 4;
				}
				Toast.makeText(thisContext, humanAge + " years old in human age.", Toast.LENGTH_LONG).show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


}
