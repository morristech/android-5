package com.nigorojr.bindservicesample;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Messenger;
import android.os.Message;
import android.os.Handler;
import android.widget.Toast;

public class BindService extends Service {
    public static final int MSG_HELLO = 1;
    private final Messenger m = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_HELLO:
                    String toShow = String.format("Hello, %s!", (String)msg.obj);
                    Toast.makeText(getApplicationContext(), toShow, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    });
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(getApplicationContext(), "Binded!", Toast.LENGTH_SHORT).show();
        return m.getBinder();
    }

}
