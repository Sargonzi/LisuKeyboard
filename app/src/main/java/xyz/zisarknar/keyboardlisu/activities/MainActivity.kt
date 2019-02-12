package xyz.zisarknar.keyboardlisu.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import xyz.zisarknar.keyboardlisu.R
import xyz.zisarknar.keyboardlisu.fragments.PrefFragment

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        cvSetting.setOnClickListener {
            var intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }

//      check if the keyboard is already enabled
        cvEnable.setOnClickListener {
            this.startActivity(Intent(Settings.ACTION_INPUT_METHOD_SETTINGS))
        }

        cvSelect.setOnClickListener {
            val inputMethodManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (inputMethodManager != null) {
                inputMethodManager.showInputMethodPicker()
            } else {
                Toast.makeText(this, "Not possible", Toast.LENGTH_SHORT).show()
            }
            false
        }

        cvAbout.setOnClickListener {
            var intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return super.onOptionsItemSelected(item)
    }
}



