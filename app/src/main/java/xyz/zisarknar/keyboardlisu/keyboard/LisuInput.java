package xyz.zisarknar.keyboardlisu.keyboard;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.KeyboardView;
import android.view.View;

/**
 * This is the main method for the input method service
 *
 * @author Zisarknar
 */

public class LisuInput extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    /**
     * Tag for the logging
     */
    public static final String TAG = "lisuInput";


    /**
     * initialize the keyboard at the app start
     */
    @Override
    public void onInitializeInterface() {
        super.onInitializeInterface();
    }


    /**
     * after the initialization method this method is called
     * check the default input and create that input method
     *
     * @return
     */
    @Override
    public View onCreateInputView() {
        return super.onCreateInputView();
    }

    @Override
    public void onPress(int i) {

    }

    @Override
    public void onRelease(int i) {

    }

    @Override
    public void onKey(int i, int[] ints) {

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

    }

    @Override
    public void swipeUp() {

    }
}
