package com.example.mapping;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class MapPreferenceActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
