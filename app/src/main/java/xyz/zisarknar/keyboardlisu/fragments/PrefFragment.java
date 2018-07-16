package xyz.zisarknar.keyboardlisu.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import xyz.zisarknar.keyboardlisu.R;

public class PrefFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {

    private CheckBoxPreference soundPref;
    private CheckBoxPreference vibPref;
    private Preference btnEnable;
    private Preference btnSelect;
    private SwitchPreference switchLisu;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_lisu);
        soundPref = (CheckBoxPreference) findPreference(getString(R.string.pref_snd));
        vibPref = (CheckBoxPreference) findPreference(getString(R.string.pref_vib));
        btnEnable = findPreference(getString(R.string.keyEnable));
        btnSelect = findPreference(getString(R.string.keySelect));
        switchLisu = (SwitchPreference) findPreference(getString(R.string.defaultLisu));
        btnEnable.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                getActivity().startActivity(new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS));
                return false;
            }
        });

        btnSelect.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null) {
                    inputMethodManager.showInputMethodPicker();
                } else {
                    Toast.makeText(getActivity(), "Not possible", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {

    }
}
