package com.nigorojr.o_timer;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;
import android.widget.TextView;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MainActivity extends Activity implements View.OnClickListener {
    
    public static final int REQUEST_TIME = 0;
    
    private OTimer timer;
    
    private Messenger messenger = null;
    private ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            messenger = new Messenger(binder);
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };
    
    private Messenger receive = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // Change the TextView to the elapsed time
            int[] value = timer.getDiff();
            if (value == null)
                return;
            int[] ids = {R.id.days, R.id.hours, R.id.minutes, R.id.seconds};
            for (int i = 0; i < value.length; i++) {
                TextView tv = (TextView)findViewById(ids[i]);
                tv.setText(String.valueOf(value[i]));
            }
            
            long timeToGoal = (Long)msg.obj;
            if (timeToGoal == -1)
                return;
            if (timeToGoal == 0)
                System.out.println("CONGRATULATIONS!!!!");
            else
                System.out.println("Time left: " + timeToGoal + " seconds");
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        timer = new OTimer();

        // Set OnClickListener to buttons
        findViewById(R.id.button_start_stop).setOnClickListener(this);
        findViewById(R.id.button_new).setOnClickListener(this);

        Timer t = new Timer();
        // Send request for time every second
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (messenger == null) {
                    return;
                }

                Message msg = Message.obtain(null, REQUEST_TIME);
                msg.replyTo = receive;
                
                try {
                    messenger.send(msg);
                }
                catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        };
        t.scheduleAtFixedRate(task, 1, 1000);

        // Bind to service
        this.bindService(new Intent(this, OTimer.class), sc, Context.BIND_AUTO_CREATE);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    /**
     * This method is called when the buttons are clicked.
     */
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_start_stop:
                if (timer.isStarted())
                    stopTimer();
                else
                    timer.start();
                break;
            case R.id.button_new:
                break;
        }
    }
    
    @Override
    /**
     * Unbind from service when Activity is destroyed
     */
    public void onDestroy() {
        super.onDestroy();

        unbindService(sc);
        Toast.makeText(getApplicationContext(), "Thanks for using this app!", Toast.LENGTH_SHORT).show();
    }
    
    public void stopTimer() {
        int[] duration = timer.stop();
        String str = duration[0] + " days " + duration[1] + " hours " + duration[2]
                + " minutes " + duration[3] + " seconds!!!!";
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
    }
}