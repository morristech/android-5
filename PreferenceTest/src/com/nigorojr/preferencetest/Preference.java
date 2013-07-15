package com.nigorojr.preferencetest;

import android.annotation.TargetApi;
import android.os.Build;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

public class Preference extends PreferenceActivity {
    @Override
    @TargetApi(11)
    @SuppressWarnings("deprecation")
    public void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 11)
            getFragmentManager().beginTransaction().replace(android.R.id.content, new PF()).commit();
        else
            addPreferencesFromResource(R.xml.pref);
    }
    
    @TargetApi(11)
    public static class PF extends PreferenceFragment {
        @Override
        public void onCreate(android.os.Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref);
        }
    }
}
