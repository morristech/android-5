package com.nigorojr.intentservice;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private static int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        findViewById(R.id.button).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (count == 0) {
					Intent intent = null;
					EditText et = (EditText)findViewById(R.id.editText);
					String str = et.getText().toString();
					// Start the sample program if the duration is specified
					if (str != null && !str.equals("")) {
				        int duration = Integer.parseInt(str);
	
						intent = new Intent(MainActivity.this, IntentServiceSample.class);
						intent.putExtra("WAIT_TIME", duration);
					}
					// Else, start the tapping game
					else {
						intent = new Intent(MainActivity.this, Tap.class);
						count++;
					}
	
					startService(intent);
				}
				// Otherwise, increment the count
				else
					count++;
			}
        });
        
        /*
         * This button resets the counter. However, it can do nothing about the ongoing thread which is waiting for
         * the 10 seconds to elapse. If the user restarts while the worker thread is still working, it will result
         * in an inaccurate counting.
         */
        findViewById(R.id.button_stop).setOnClickListener(new OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		Toast.makeText(MainActivity.this, "Stopped", Toast.LENGTH_SHORT).show();
        		resetCount();
        	}
        });
    }

    /**
     * Returns the current count (the number of times the button was tapped)
     * @return The number of times the button was tapped, including the first tap
     */
    public static int getCount() {
    	return count;
    }
    
    /**
     * Resets the count to 0 in order to prepare for a new round.
     */
    public static void resetCount() {
    	count = 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
