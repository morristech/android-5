package com.nigorojr.dialogtest;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.Menu;
import android.view.View;
import android.app.Dialog;
import android.app.AlertDialog.Builder;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        findViewById(R.id.button_show_dialog).setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_show_dialog:
//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setTitle("This is the set title");
//                builder.setMessage("This is the message. OK?");
//                AlertDialog dialog = builder.create();
//                dialog.show();
                showDialog(0);
                break;
        }
    }
    
    @Override
    public Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        switch (id) {
            case 0:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Here's the title");
                builder.setMessage("And here's the message");
                dialog = builder.create();
                break;
        }
        return dialog;
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
