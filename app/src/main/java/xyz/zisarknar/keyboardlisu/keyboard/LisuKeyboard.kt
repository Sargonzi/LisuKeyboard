package xyz.zisarknar.keyboardlisu.keyboard

import android.content.Context
import android.inputmethodservice.Keyboard

class LisuKeyboard : Keyboard {

    constructor(context: Context, xmlLayoutResId: Int) : super(context, xmlLayoutResId) {}

    constructor(context: Context, xmlLayoutResId: Int, modeId: Int, width: Int, height: Int) : super(context, xmlLayoutResId, modeId, width, height) {}

    constructor(context: Context, xmlLayoutResId: Int, modeId: Int) : super(context, xmlLayoutResId, modeId) {}

    constructor(context: Context, layoutTemplateResId: Int, characters: CharSequence, columns: Int, horizontalPadding: Int) : super(context, layoutTemplateResId, characters, columns, horizontalPadding) {}
}
