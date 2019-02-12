package xyz.zisarknar.keyboardlisu

import android.app.Application
import android.graphics.Typeface
import xyz.zisarknar.keyboardlisu.utils.FontOverride

class LKApp : Application() {

    companion object {
        private lateinit var lisuTypeface: Typeface
    }

    override fun onCreate() {
        super.onCreate()
        lisuTypeface = Typeface.createFromAsset(this.assets, "fonts/Lisu.ttf")
        FontOverride.setDefaultFont(applicationContext, "DEFAULT", "fonts/Lisu.ttf")
        FontOverride.setDefaultFont(applicationContext, "MONOSPACE", "fonts/Lisu.ttf")
    }

}