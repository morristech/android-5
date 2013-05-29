package com.nigorojr.implicit_intent_2;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.SearchManager;
import android.view.Menu;
import android.view.View.OnClickListener;
import android.view.View;
import android.content.Intent;

public class MainActivity extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button_search).setOnClickListener(this);
        findViewById(R.id.button_call).setOnClickListener(this);
        findViewById(R.id.button_map).setOnClickListener(this);
        findViewById(R.id.button_audio).setOnClickListener(this);
        findViewById(R.id.button_image).setOnClickListener(this);
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
            case R.id.button_search:
                intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, "Android");
                // If you want to specify the URL,
                // Uri uri = Uri.parse("http://developer.android.com");
                // intent = new Intent(Intent.ACTION_VIEW, uri);
                break;
            case R.id.button_call:
                intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:01234567890"));
                // Also need permission in the Manifest file
                break;
            case R.id.button_map:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:36.682, 139.691?z=16"));
                // If you want to send an email,
                // intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:example@example.net");
                break;
            case R.id.button_audio:
                intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("audio/*");
                break;
            case R.id.button_image:
                intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                break;
        }
        if (intent != null)
            startActivity(intent);
    }
}
