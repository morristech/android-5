package com.nigorojr.bindservicesample;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {

    // Messenger that communicates with the service
    Messenger mService = null;
    // Flag that indicates whether the service has been started correctly
    boolean mBound;

    private final ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            mService = new Messenger(service);
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName className) {
            mBound = false;
        }
    };
    
    /**
     * Binds to the service.
     */
    public void bind() {
        bindService(new Intent(this, BindService.class), mConnection, Context.BIND_AUTO_CREATE);
    }
    
    /**
     * Unbinds from the service
     */
    public void unbind() {
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }
    
    /**
     * Creates a message and pass it to the service.
     */
    public void sayHi(String msg) {
        if (!mBound)
            return;
        
        Message message = Message.obtain(null, BindService.MSG_SAY_HI, 0, 0, msg);
        
        try {
            mService.send(message);
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ClickListener cl = new ClickListener();
        findViewById(R.id.button_bind).setOnClickListener(cl);
        findViewById(R.id.button_msg_android).setOnClickListener(cl);
        findViewById(R.id.button_msg_message).setOnClickListener(cl);
        findViewById(R.id.button_unbind).setOnClickListener(cl);
    }

    class ClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_bind:
                    bind();
                    break;
                case R.id.button_msg_android:
                    sayHi("Android");
                    break;
                case R.id.button_msg_message:
                    sayHi("Message");
                    break;
                case R.id.button_unbind:
                    unbind();
                    break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
