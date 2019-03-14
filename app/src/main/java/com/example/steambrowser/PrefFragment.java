package com.example.steambrowser;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

public class PrefFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.prefs);
    }
}

