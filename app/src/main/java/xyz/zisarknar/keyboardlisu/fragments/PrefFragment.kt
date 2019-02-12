package xyz.zisarknar.keyboardlisu.fragments

import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import android.preference.CheckBoxPreference
import android.preference.PreferenceFragment
import android.preference.SwitchPreference
import xyz.zisarknar.keyboardlisu.R

class PrefFragment : PreferenceFragment(), OnSharedPreferenceChangeListener {

    private var soundPref: CheckBoxPreference? = null
    private var vibPref: CheckBoxPreference? = null
    private var switchLisu: SwitchPreference? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preference_lisu)
        soundPref = findPreference(getString(R.string.pref_snd)) as CheckBoxPreference
        vibPref = findPreference(getString(R.string.pref_vib)) as CheckBoxPreference
        switchLisu = findPreference(getString(R.string.defaultLisu)) as SwitchPreference

    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, s: String) {

    }
}
