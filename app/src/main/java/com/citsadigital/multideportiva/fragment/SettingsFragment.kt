package com.citsadigital.multideportiva.fragment


import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import android.util.Log
import com.citsadigital.multideportiva.R


class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.pref_settings)
        Log.d(javaClass.name, "New Setting")

    }


}
