package com.nigorojr.fileinputoutputsample;

import android.annotation.TargetApi;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.os.Bundle;
import android.os.Build;

public class MainPreferenceActivity extends PreferenceActivity {
    @TargetApi(11)
    @SuppressWarnings("deprecation")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 11)
            getFragmentManager().beginTransaction().replace(android.R.id.content, new PreferenceFragment() {
                @Override
                public void onCreate(Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState); // This is important
                    addPreferencesFromResource(R.xml.preference_layout);
                }
            }).commit();
        // For API < 11
        else {
            // DEBUG
            System.out.println("Used the addPreferencesFromResource in PreferenceActivity");
            addPreferencesFromResource(R.xml.preference_layout);
        }
    }
}
