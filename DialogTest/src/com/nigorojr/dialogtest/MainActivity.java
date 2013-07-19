package com.nigorojr.dialogtest;

import android.os.Bundle;
import android.app.AlertDialog;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;
import android.app.Dialog;
import android.content.DialogInterface;
// import android.app.DialogFragment;
import android.support.v4.app.DialogFragment;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    
    public static final int ID_FOR_DIALOG = 0;

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
                // FragmentManager fm = getFragmentManager();
                FragmentManager fm = getSupportFragmentManager();
                new DialogFragmentClass().show(fm, "what's a tag");
                break;
        }
    }
    
    private static AlertDialog d2 = null;
    public static class DialogFragmentClass extends DialogFragment implements DialogInterface.OnClickListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Here's the title");
//            builder.setMessage("And here's the message");
            
            // If you want to have buttons on the dialog
            builder.setPositiveButton("Positive!", this);
            builder.setNeutralButton("Neutral!", this);
            builder.setNegativeButton("Negative!", this);
            String[] items = {"Hello", "world", "this", "is", "an", "array", "!!!"};
            builder.setItems(items, this);

//            Dialog dialog = builder.create();
//            return dialog;
            d2 = builder.create();
            return d2;
        }
        
        // Called when dialog is tapped
        @Override
        public void onClick(DialogInterface dialog, int id) {
            switch (id) {
                case AlertDialog.BUTTON_POSITIVE:
                    Toast.makeText(getActivity(), "Positive button clicked!", Toast.LENGTH_SHORT).show();
                    break;
                case AlertDialog.BUTTON_NEUTRAL:
                    Toast.makeText(getActivity(), "Neutral button clicked!", Toast.LENGTH_SHORT).show();
                    break;
                case AlertDialog.BUTTON_NEGATIVE:
                    Toast.makeText(getActivity(), "Negative button clicked!", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(getActivity(), d2.getListView().getAdapter().getItem(id).toString(), Toast.LENGTH_SHORT).show();
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
