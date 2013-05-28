package com.nigorojr.implicit_intent;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button_1).setOnClickListener(this);
        findViewById(R.id.button_2).setOnClickListener(this);
        findViewById(R.id.button_3).setOnClickListener(this);
        findViewById(R.id.button_4).setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.button_1:
                intent = new Intent("com.nigorojr.animalview.action.VIEW", Uri.parse("zoo://animal/giraffe"));
                break;
            case R.id.button_2:
                intent = new Intent("com.nigorojr.animalview.action.VIEW", Uri.parse("zoo://animal/lion"));
                break;
            case R.id.button_3:
                intent = new Intent("com.nigorojr.animalview.action.VIEW", Uri.parse("zoo://animal/hippo"));
                break;
            case R.id.button_4:
                intent = new Intent("com.nigorojr.animalview.action.VIEW");
                break;
        }
        if (intent != null)
            startActivity(intent);
    }

}
