package xyz.zisarknar.keyboardlisu.keyboard;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputConnection;

import xyz.zisarknar.keyboardlisu.R;

/**
 * This is the main method for the input method service
 *
 * @author Zisarknar
 */

public class LisuInput extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    /**
     * Tag for the logging
     */
    private static final String TAG = "lisuInput";

    /**
     * Custom key code for language change
     */
    private static final int KEYCODE_LANG_CHANGE = -101;
    private static final int KEYCODE_LISU_SHIFT = -102;

    private LisuKeyboardView mInputView;
    private LisuKeyboard mCurrentKeyboard;
    private LisuKeyboard mStandardKeyboard;
    private LisuKeyboard mStandardLisuKeyboard;
    private LisuKeyboard mShiftedLisuKeyboard;
    private LisuKeyboard mStandardSymbol;
    private LisuKeyboard mShiftedSymbol;

    private int mLastDisplayWidth;
    private boolean caps = false;
    private int recentKeyboard = 0;


    /**
     * Main initialization method for this class
     * be sure to call super method
     */
    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * initialize the keyboard at the app start
     */
    @Override
    public void onInitializeInterface() {
        super.onInitializeInterface();
        if (mStandardKeyboard != null) {
            int displayWidth = getMaxWidth();
            if (displayWidth == mLastDisplayWidth) return;
            mLastDisplayWidth = displayWidth;
        }
        mStandardKeyboard = new LisuKeyboard(this, R.xml.qwerty);
        mStandardLisuKeyboard = new LisuKeyboard(this, R.xml.qwerty_lisu_std);
        mShiftedLisuKeyboard = new LisuKeyboard(this, R.xml.qwerty_lisu_shift);
        mStandardSymbol = new LisuKeyboard(this, R.xml.symbols);
        mShiftedSymbol = new LisuKeyboard(this, R.xml.symbols2);
    }


    /**
     * after the initialization method this method is called
     * check the default input and create that input method
     *
     * @return
     */
    @Override
    public View onCreateInputView() {
        mInputView = (LisuKeyboardView) getLayoutInflater().inflate(R.layout.keyboard_view_lisu, null);
        mInputView.setOnKeyboardActionListener(this);
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(getString(R.string.defaultLisu), false)){
            mInputView.setKeyboard(mStandardLisuKeyboard);
        } else {
            mInputView.setKeyboard(mStandardKeyboard);
        }
        return mInputView;
    }

    @Override
    public void onPress(int keyCode) {
        //check if the snd check box is ON
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(getString(R.string.pref_snd), false)) {
            playClick(keyCode);
        }

        //check if the vib check box is ON
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(getString(R.string.pref_vib), false)) {
            vibrate();
        }

    }

    @Override
    public void onRelease(int i) {
    }

    @Override
    public void onKey(int primaryCode, int[] ints) {
        InputConnection ic = getCurrentInputConnection();
        mCurrentKeyboard = (LisuKeyboard) mInputView.getKeyboard();

        switch (primaryCode) {
            // change character view to symbol view and vice versa
            case LisuKeyboard.KEYCODE_MODE_CHANGE:
                if (mCurrentKeyboard == mStandardKeyboard) {
                    recentKeyboard = 0;
                    mInputView.setKeyboard(mStandardSymbol);
                } else if (mCurrentKeyboard == mStandardLisuKeyboard || mCurrentKeyboard == mShiftedLisuKeyboard) {
                    recentKeyboard = 1;
                    mInputView.setKeyboard(mStandardSymbol);
                } else {
                    if (recentKeyboard == 0) {
                        mInputView.setKeyboard(mStandardKeyboard);
                    } else if (recentKeyboard == 1) {
                        mInputView.setKeyboard(mStandardLisuKeyboard);
                    }
                }
                break;

            // change english between lisu keyboard view
            case KEYCODE_LANG_CHANGE:
                if (mCurrentKeyboard == mStandardKeyboard) {
                    mInputView.setKeyboard(mStandardLisuKeyboard);
                } else {
                    mInputView.setKeyboard(mStandardKeyboard);
                    caps = false;
                    mInputView.setShifted(caps);
                    mInputView.invalidateAllKeys();
                }
                break;

            case LisuKeyboard.KEYCODE_DELETE:
                CharSequence selectedText = ic.getSelectedText(0);
                if (TextUtils.isEmpty(selectedText)) {
                    ic.deleteSurroundingText(1, 0);
                } else {
                    ic.commitText("", 1);
                }
                break;

            //for english keyboard
            //and symbol
            case LisuKeyboard.KEYCODE_SHIFT:
                if (mCurrentKeyboard == mStandardKeyboard) {
                    caps = !caps;
                    mCurrentKeyboard.setShifted(caps);
                    mInputView.invalidateAllKeys();
                } else {
                    if (mCurrentKeyboard == mStandardSymbol) {
                        mInputView.setKeyboard(mShiftedSymbol);
                    } else if (mCurrentKeyboard == mShiftedSymbol) {
                        mInputView.setKeyboard(mStandardSymbol);
                    }
                }
                break;

            // for lisu key shift
            case KEYCODE_LISU_SHIFT:
                if (mCurrentKeyboard == mStandardLisuKeyboard) {
                    mInputView.setKeyboard(mShiftedLisuKeyboard);
                } else {
                    mInputView.setKeyboard(mStandardLisuKeyboard);
                }
                break;

            default:
                char code = (char) primaryCode;
                // check if the shift key is On change small letter to capital letter
                if (Character.isLetter(code) && caps) {
                    code = Character.toUpperCase(code);
                }
                if (mCurrentKeyboard == mStandardKeyboard) {
                    caps = false;
                    mCurrentKeyboard.setShifted(caps);
                    mInputView.invalidateAllKeys();
                }
                ic.commitText(String.valueOf(code), 1);
        }

    }

    private void vibrate() {
        Vibrator v = (Vibrator) getBaseContext().getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(40);
    }

    private void playClick(int keyCode) {
        AudioManager am = (AudioManager) getSystemService(AUDIO_SERVICE);
        switch (keyCode) {
            case 32:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                break;
            case Keyboard.KEYCODE_DONE:
            case 10:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
                break;
            case Keyboard.KEYCODE_DELETE:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE);
                break;
            default:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
        }
    }

    @Override
    public void onText(CharSequence charSequence) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {
        requestHideSelf(1);

    }

    @Override
    public void swipeUp() {

    }
}
