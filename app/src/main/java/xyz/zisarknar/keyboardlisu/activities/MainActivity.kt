package xyz.zisarknar.keyboardlisu.activities

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import xyz.zisarknar.keyboardlisu.R
import xyz.zisarknar.keyboardlisu.fragments.PrefFragment

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, PrefFragment()).commit()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
    }
}



