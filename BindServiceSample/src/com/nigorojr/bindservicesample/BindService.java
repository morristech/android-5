package com.nigorojr.bindservicesample;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.widget.Toast;

public class BindService extends Service {
    
    public static final int MSG_SAY_HI = 1;
    private final Messenger mMessenger = new Messenger(new IncomingHandler());
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SAY_HI:
                    String message = "Hello from " + msg.obj;
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(getApplicationContext(), "onBind() was called", Toast.LENGTH_SHORT).show();
        return mMessenger.getBinder();
    }

}
