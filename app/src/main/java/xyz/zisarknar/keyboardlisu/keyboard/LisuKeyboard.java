package xyz.zisarknar.keyboardlisu.keyboard;

import android.content.Context;
import android.inputmethodservice.Keyboard;

public class LisuKeyboard extends Keyboard {

    public LisuKeyboard(Context context, int xmlLayoutResId) {
        super(context, xmlLayoutResId);
    }

    public LisuKeyboard(Context context, int xmlLayoutResId, int modeId, int width, int height) {
        super(context, xmlLayoutResId, modeId, width, height);
    }

    public LisuKeyboard(Context context, int xmlLayoutResId, int modeId) {
        super(context, xmlLayoutResId, modeId);
    }

    public LisuKeyboard(Context context, int layoutTemplateResId, CharSequence characters, int columns, int horizontalPadding) {
        super(context, layoutTemplateResId, characters, columns, horizontalPadding);
    }
}
