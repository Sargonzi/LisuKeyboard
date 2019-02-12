package xyz.zisarknar.keyboardlisu.keyboard

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.NinePatchDrawable
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.util.AttributeSet

import xyz.zisarknar.keyboardlisu.R

class LisuKeyboardView : KeyboardView {

    private var mTransparent: Drawable = ColorDrawable(Color.TRANSPARENT)
    private var mSpaceBackground = context.resources.getDrawable(R.drawable.space) as NinePatchDrawable
    private var mPressedBackground = context.resources.getDrawable(R.drawable.press) as NinePatchDrawable
    private val mPaint: Paint? = null

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val keys = keyboard.keys

        for (key in keys) {

            if (key.label != null) {
                if (key.codes[0] == 32) {
                    mSpaceBackground.setBounds(key.x, key.y, key.x + key.width, key.y + key.height)
                    mSpaceBackground.draw(canvas)
                }

            }
        }
    }

}
