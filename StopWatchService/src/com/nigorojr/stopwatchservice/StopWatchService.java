package com.nigorojr.stopwatchservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Messenger;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class StopWatchService extends Service {
    Timer timer = new Timer();
    boolean pause = false;
    long currentTimeInMilliSec = 0;
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            if (!pause)
                currentTimeInMilliSec++;
        }
    };
    
    @Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer();
        pause = true;
        currentTimeInMilliSec = 0;
        timer.scheduleAtFixedRate(task, 0, 1);
    }

    Messenger reply = null;
    Messenger messenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            reply = msg.replyTo;

            switch (msg.what) {
                case MainActivity.MSG_STOPWATCH:
                    action((String)msg.obj);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    });
    
    public void action(String str) {
        if (str.equals("start")) {
            pause = false;
        }
        else if (str.equals("stop")) {
            pause = true;
            currentTimeInMilliSec = 0;
        }
        else if (str.equals("pause")) {
            pause = true;
        }
        else if (str.equals("show")) {
            if (reply == null)
                return;
            try {
                reply.send(Message.obtain(null, MainActivity.MSG_STOPWATCH, currentTimeInMilliSec + ""));
            }
            catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(getApplicationContext(), "OK, let's rock!!", Toast.LENGTH_SHORT).show();
        return messenger.getBinder();
    }
}
