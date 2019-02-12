package xyz.zisarknar.keyboardlisu.activities

import android.os.Bundle
import xyz.zisarknar.keyboardlisu.R
import xyz.zisarknar.keyboardlisu.fragments.PrefFragment

class SettingActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentSetting, PrefFragment()).commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
    }
}