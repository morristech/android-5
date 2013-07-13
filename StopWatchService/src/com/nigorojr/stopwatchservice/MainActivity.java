package com.nigorojr.stopwatchservice;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.os.Messenger;
import android.os.Message;
import android.os.RemoteException;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.content.ComponentName;
import android.content.Intent;
import android.content.Context;
import android.os.Handler;
import android.widget.TextView;

public class MainActivity extends Activity {
    public static final int MSG_STOPWATCH = 1;

    TextView tv = null;
    boolean serviceConnected = false;
    Messenger messenger = null;
    ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            messenger = new Messenger(service);
            serviceConnected = true;
        }
        
        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceConnected = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView)findViewById(R.id.passed_time);
        findViewById(R.id.btn_bind).setOnClickListener(new ClickListener());
        findViewById(R.id.btn_start).setOnClickListener(new ClickListener());
        findViewById(R.id.btn_stop).setOnClickListener(new ClickListener());
        findViewById(R.id.btn_pause).setOnClickListener(new ClickListener());
        findViewById(R.id.btn_show).setOnClickListener(new ClickListener());
        findViewById(R.id.btn_unbind).setOnClickListener(new ClickListener());
    }
    
    public void bind() {
        bindService(new Intent(this, StopWatchService.class), sc, Context.BIND_AUTO_CREATE);
        serviceConnected = true;
    }

    public void start() {
        if (!serviceConnected)
            return;
        Message msg = Message.obtain(null, MSG_STOPWATCH, "start");
        try {
            messenger.send(msg);
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    
    public void stop() {
        if (!serviceConnected)
            return;
        Message msg = Message.obtain(null, MSG_STOPWATCH, "stop");
        try {
            messenger.send(msg);
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    
    public void pause() {
        if (!serviceConnected)
            return;
        Message msg = Message.obtain(null, MSG_STOPWATCH, "pause");
        try {
            messenger.send(msg);
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    
    public void show() {
        if (!serviceConnected)
            return;
        Message msg = Message.obtain(null, MSG_STOPWATCH, "show");
        // msg.replyTo = messenger;
        msg.replyTo = receive;
        try {
            messenger.send(msg);
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    
    Messenger receive = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_STOPWATCH:
                    tv.setText((String)msg.obj);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    });
    
    public void unbind() {
        if (!serviceConnected)
            return;
        unbindService(sc);
        serviceConnected = false;
    }

    class ClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_bind:
                    bind();
                    break;
                case R.id.btn_start:
                    start();
                    break;
                case R.id.btn_stop:
                    stop();
                    break;
                case R.id.btn_pause:
                    pause();
                    break;
                case R.id.btn_show:
                    show();
                    break;
                case R.id.btn_unbind:
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
