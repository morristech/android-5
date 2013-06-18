package com.nigorojr.intentservice;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

public class IntentServiceSample extends IntentService {

	public IntentServiceSample() {
		super("IntentServiceSample");
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		Toast.makeText(this, "The service has been created!", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		int waitTime = intent.getIntExtra("WAIT_TIME", 1000);
		
		synchronized(this) {
			try {
				wait(waitTime);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		
		Toast.makeText(this, "The service has been destroyed...", Toast.LENGTH_SHORT).show();
	}
}
