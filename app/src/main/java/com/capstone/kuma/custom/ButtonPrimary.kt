package com.capstone.kuma.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.capstone.kuma.R

class ButtonPrimary: AppCompatButton {
    private var txtColor: Int = 0
    private var enabledBackgroundColor: Drawable? = null
    private var disabledBackgroundColor: Drawable? = null

    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        updateButton()
    }

    private fun init() {
        txtColor = ContextCompat.getColor(context, android.R.color.background_light)
        enabledBackgroundColor = ContextCompat.getDrawable(context, R.drawable.bg_en_button)
        disabledBackgroundColor = ContextCompat.getDrawable(context, R.drawable.bg_dis_button)
        updateButton()
    }

    private fun updateButton() {
        val isEnabled = isEnabled
        background = if (isEnabled) enabledBackgroundColor else disabledBackgroundColor
        textSize = 16f
        gravity = Gravity.CENTER
        setTextColor(ContextCompat.getColor(context, android.R.color.white))
    }
}