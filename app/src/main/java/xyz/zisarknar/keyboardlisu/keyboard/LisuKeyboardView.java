package xyz.zisarknar.keyboardlisu.keyboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;

import java.util.List;

import xyz.zisarknar.keyboardlisu.R;

public class LisuKeyboardView extends KeyboardView {

    Drawable mTransparent = new ColorDrawable(Color.TRANSPARENT);
    NinePatchDrawable mSpaceBackground = (NinePatchDrawable) getContext().getResources().getDrawable(R.drawable.space);
    NinePatchDrawable mPressedBackground = (NinePatchDrawable) getContext().getResources().getDrawable(R.drawable.press);
    private Paint mPaint;

    public LisuKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LisuKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LisuKeyboardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        List<Keyboard.Key> keys = getKeyboard().getKeys();

        for (Keyboard.Key key : keys) {

            if (key.label != null) {
                if (key.codes[0] == 32) {
                    mSpaceBackground.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
                    mSpaceBackground.draw(canvas);
                }

            }
        }
    }

}
