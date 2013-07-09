package com.nigorojr.bindservicesample;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Messenger;
import android.os.Message;
import android.os.RemoteException;
import android.content.ServiceConnection;

public class MainActivity extends Activity {
    
    public static final String CUSTOM_MESSAGE = "com.nigorojr.bindservicesample.custom_message";
    public static final int REQUEST_CODE = 1;
    private Messenger m = null;
    private boolean serviceGoingOn = false;
    private final ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            m = new Messenger(service);
            serviceGoingOn = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // serviceGoingOn = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button[] buttons = new Button[4];
        buttons[0] = (Button)findViewById(R.id.button_bind);
        buttons[1] = (Button)findViewById(R.id.button_unbind);
        buttons[2] = (Button)findViewById(R.id.button_msg_android);
        buttons[3] = (Button)findViewById(R.id.button_msg_custom);
        for (Button b : buttons)
            b.setOnClickListener(new ClickListener());
    }
    
    private void bind() {
        bindService(new Intent(this, BindService.class), sc, Context.BIND_AUTO_CREATE);
    }

    private void unbind() {
        if (serviceGoingOn)
            unbindService(sc);
        serviceGoingOn = false;
    }

    private void sendMessage(String text) {
        if (!serviceGoingOn)
            return;
        
        Message msg = Message.obtain(null, BindService.MSG_HELLO, text);
        try {
            m.send(msg);
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(View v) {
        if (!serviceGoingOn)
            return;
        Intent i = new Intent(v.getContext(), CustomMessage.class);
        startActivityForResult(i, REQUEST_CODE);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode != REQUEST_CODE)
            return;
        if (resultCode == CustomMessage.RESULT_OK)
            sendMessage(intent.getStringExtra(CUSTOM_MESSAGE));
        else
            Toast.makeText(getApplicationContext(), "Canceled", Toast.LENGTH_SHORT).show();
    }

    class ClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_bind:
                    bind();
                    break;
                case R.id.button_unbind:
                    unbind();
                    break;
                case R.id.button_msg_android:
                    sendMessage("Android");
                    break;
                case R.id.button_msg_custom:
                    sendMessage(v);
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
