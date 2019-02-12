package xyz.zisarknar.keyboardlisu.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import android.preference.CheckBoxPreference
import android.preference.Preference
import android.preference.PreferenceFragment
import android.preference.SwitchPreference
import android.provider.Settings
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

import xyz.zisarknar.keyboardlisu.R

class PrefFragment : PreferenceFragment(), OnSharedPreferenceChangeListener {

    private var soundPref: CheckBoxPreference? = null
    private var vibPref: CheckBoxPreference? = null
    private var btnEnable: Preference? = null
    private var btnSelect: Preference? = null
    private var switchLisu: SwitchPreference? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preference_lisu)
        soundPref = findPreference(getString(R.string.pref_snd)) as CheckBoxPreference
        vibPref = findPreference(getString(R.string.pref_vib)) as CheckBoxPreference
        btnEnable = findPreference(getString(R.string.keyEnable))
        btnSelect = findPreference(getString(R.string.keySelect))
        switchLisu = findPreference(getString(R.string.defaultLisu)) as SwitchPreference
        btnEnable!!.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            activity.startActivity(Intent(Settings.ACTION_INPUT_METHOD_SETTINGS))
            false
        }

        btnSelect!!.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (inputMethodManager != null) {
                inputMethodManager.showInputMethodPicker()
            } else {
                Toast.makeText(activity, "Not possible", Toast.LENGTH_SHORT).show()
            }
            false
        }

    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, s: String) {

    }
}
