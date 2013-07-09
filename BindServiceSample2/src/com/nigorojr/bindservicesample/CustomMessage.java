package com.nigorojr.bindservicesample;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;

public class CustomMessage extends Activity {
    EditText et;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_custom);
        
        et = (EditText)findViewById(R.id.editText);
        Button btn = (Button)findViewById(R.id.button_OK);
        btn.setOnClickListener(new ClickListener());
    }
    
    class ClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            i = new Intent(v.getContext(), MainActivity.class);
            if (et != null && et.getText() != null)
                i.putExtra(MainActivity.CUSTOM_MESSAGE, et.getText().toString());
            setResult(RESULT_OK, i);
            finish();
        }
    }
}
