package com.nigorojr.sharedpreferencessample;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceActivity;

//@SuppressLint("NewApi")
public class PreferencesSample extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_sample);
    }
}
