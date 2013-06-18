package com.nigorojr.intentservice;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

public class Tap extends IntentService {
	
	public Tap() {
		super("Tap");
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Toast.makeText(this, "TAP!!!", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		synchronized(this) {
			try {
				wait(10000);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Displays the toast showing the speed of the tapping.
	 * Because the duration is 10 seconds, it divides the number times the button was tapped by 10
	 * to get the tap-per-second.
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		Toast.makeText(this, "Tap speed was " + (MainActivity.getCount() / 10.0) + " taps per second!", Toast.LENGTH_LONG).show();
		MainActivity.resetCount();
	}
}
