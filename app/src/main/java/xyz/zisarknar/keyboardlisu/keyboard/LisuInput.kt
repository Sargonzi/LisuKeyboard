package xyz.zisarknar.keyboardlisu.keyboard

import android.content.Context
import android.inputmethodservice.InputMethodService
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.media.AudioManager
import android.os.Handler
import android.os.Looper
import android.os.Vibrator
import android.preference.PreferenceManager
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.ViewConfiguration
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import xyz.zisarknar.keyboardlisu.R
import java.util.*

/**
 * This is the main method for the input method service
 *
 * @author Zisarknar
 */

class LisuInput : InputMethodService(), KeyboardView.OnKeyboardActionListener {

    private var mInputView: LisuKeyboardView? = null
    private var mCurrentKeyboard: LisuKeyboard? = null
    private var mStandardKeyboard: LisuKeyboard? = null
    private var mStandardLisuKeyboard: LisuKeyboard? = null
    private var mShiftedLisuKeyboard: LisuKeyboard? = null
    private var mStandardSymbol: LisuKeyboard? = null
    private var mShiftedSymbol: LisuKeyboard? = null

    private var mLastDisplayWidth: Int = 0
    private var caps = false
    private var recentKeyboard = 0
    private var timerLongPress: Timer? = null
    private var isLongPress = false


    /**
     * Main initialization method for this class
     * be sure to call super method
     */
    override fun onCreate() {
        super.onCreate()
        timerLongPress = Timer()
    }

    /**
     * initialize the keyboard at the app start
     */
    override fun onInitializeInterface() {
        super.onInitializeInterface()
        if (mStandardKeyboard != null) {
            val displayWidth = maxWidth
            if (displayWidth == mLastDisplayWidth) return
            mLastDisplayWidth = displayWidth
        }
        mStandardKeyboard = LisuKeyboard(this, R.xml.qwerty)
        mStandardLisuKeyboard = LisuKeyboard(this, R.xml.qwerty_lisu_std)
        mShiftedLisuKeyboard = LisuKeyboard(this, R.xml.qwerty_lisu_shift)
        mStandardSymbol = LisuKeyboard(this, R.xml.symbols)
        mShiftedSymbol = LisuKeyboard(this, R.xml.symbols2)
    }


    /**
     * after the initialization method this method is called
     * check the default input and create that input method
     *
     * @return
     */
    override fun onCreateInputView(): View? {
        mInputView = layoutInflater.inflate(R.layout.keyboard_view_lisu, null) as LisuKeyboardView
        mInputView!!.setOnKeyboardActionListener(this)
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(getString(R.string.defaultLisu), false)) {
            mInputView!!.keyboard = mStandardLisuKeyboard
        } else {
            mInputView!!.keyboard = mStandardKeyboard
        }
        return mInputView
    }

    override fun onPress(keyCode: Int) {
        //check if the snd check box is ON
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(getString(R.string.pref_snd), false)) {
            playClick(keyCode)
        }

        //check if the vib check box is ON
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(getString(R.string.pref_vib), false)) {
            vibrate()
        }
        timerLongPress!!.cancel()
        timerLongPress = Timer()
        timerLongPress!!.schedule(object : TimerTask() {
            override fun run() {
                try {
                    val uiHandler = Handler(Looper.getMainLooper())
                    val runnable = Runnable {
                        try {
                            this@LisuInput.onKeyLongPress(keyCode)
                        } catch (e: Exception) {
                            Log.e(TAG, "uiHandler.run: " + e.message)
                        }
                    }
                    uiHandler.post(runnable)
                } catch (e: Exception) {
                    Log.e(TAG, "Timer.run: " + e.message, e)
                }

            }
        }, ViewConfiguration.getLongPressTimeout().toLong())

    }

    /*
        long key press and show popup but don't change the keyboard view
     */
    private fun onKeyLongPress(primaryCode: Int) {
        if (primaryCode == -101) {
            isLongPress = true
            changeKeyboard()
        }
    }

    override fun onRelease(i: Int) {
        timerLongPress!!.cancel()
    }

    override fun onKey(primaryCode: Int, ints: IntArray) {
        val ic = currentInputConnection
        mCurrentKeyboard = mInputView!!.keyboard as LisuKeyboard

        when (primaryCode) {
            // change character view to symbol view and vice versa
            LisuKeyboard.KEYCODE_MODE_CHANGE -> if (mCurrentKeyboard === mStandardKeyboard) {
                recentKeyboard = 0
                mInputView!!.keyboard = mStandardSymbol
            } else if (mCurrentKeyboard === mStandardLisuKeyboard || mCurrentKeyboard === mShiftedLisuKeyboard) {
                recentKeyboard = 1
                mInputView!!.keyboard = mStandardSymbol
            } else {
                if (recentKeyboard == 0) {
                    mInputView!!.keyboard = mStandardKeyboard
                } else if (recentKeyboard == 1) {
                    mInputView!!.keyboard = mStandardLisuKeyboard
                }
            }

            // change english between lisu keyboard view
            KEYCODE_LANG_CHANGE -> if (mCurrentKeyboard === mStandardKeyboard) {
                mInputView!!.keyboard = mStandardLisuKeyboard
            } else {
                mInputView!!.keyboard = mStandardKeyboard
                caps = false
                mInputView!!.isShifted = caps
                mInputView!!.invalidateAllKeys()
            }

            LisuKeyboard.KEYCODE_DELETE -> {
                val selectedText = ic.getSelectedText(0)
                if (TextUtils.isEmpty(selectedText)) {
                    ic.deleteSurroundingText(1, 0)
                } else {
                    ic.commitText("", 1)
                }
            }

            //for english keyboard
            //and symbol
            LisuKeyboard.KEYCODE_SHIFT -> if (mCurrentKeyboard === mStandardKeyboard) {
                caps = !caps
                mCurrentKeyboard!!.isShifted = caps
                mInputView!!.invalidateAllKeys()
            } else {
                if (mCurrentKeyboard === mStandardSymbol) {
                    mInputView!!.keyboard = mShiftedSymbol
                } else if (mCurrentKeyboard === mShiftedSymbol) {
                    mInputView!!.keyboard = mStandardSymbol
                }
            }

            // for lisu key shift
            KEYCODE_LISU_SHIFT -> if (mCurrentKeyboard === mStandardLisuKeyboard) {
                mInputView!!.keyboard = mShiftedLisuKeyboard
            } else {
                mInputView!!.keyboard = mStandardLisuKeyboard
            }

            else -> {
                var code = primaryCode.toChar()
                // check if the shift key is On change small letter to capital letter
                if (Character.isLetter(code) && caps) {
                    code = Character.toUpperCase(code)
                }
                if (mCurrentKeyboard === mStandardKeyboard) {
                    caps = false
                    mCurrentKeyboard!!.isShifted = caps
                    mInputView!!.invalidateAllKeys()
                }
                ic.commitText(code.toString(), 1)
            }
        }

    }

    private fun vibrate() {
        val v = baseContext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        v.vibrate(40)
    }

    private fun playClick(keyCode: Int) {
        val am = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        when (keyCode) {
            32 -> am.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR)
            Keyboard.KEYCODE_DONE, 10 -> am.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN)
            Keyboard.KEYCODE_DELETE -> am.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE)
            else -> am.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD)
        }
    }

    override fun onText(charSequence: CharSequence) {

    }

    override fun swipeLeft() {

    }

    override fun swipeRight() {

    }

    override fun swipeDown() {
        requestHideSelf(0)

    }

    override fun swipeUp() {

    }

    /**
     * to make easy switching the keyboard when typing
     */
    private fun changeKeyboard() {
        val inputMethodManager = applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (inputMethodManager != null) {
            inputMethodManager.showInputMethodPicker()
        } else {
            Toast.makeText(applicationContext, "Not possible", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {

        /**
         * Tag for the logging
         */
        private val TAG = "lisuInput"

        /**
         * Custom key code for language change
         */
        private const val KEYCODE_LANG_CHANGE = -101
        private const val KEYCODE_LISU_SHIFT = -102
    }
}
