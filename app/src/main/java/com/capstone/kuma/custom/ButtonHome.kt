package com.capstone.kuma.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.capstone.kuma.R
import kotlin.math.round

class ButtonHome: AppCompatButton {
    private var txtColor: Int = 0
    private var backgroundColor: Drawable? = null

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
        backgroundColor = ContextCompat.getDrawable(context, R.drawable.bg_home_button)
        updateButton()
    }

    private fun updateButton() {
        background = backgroundColor
        textSize = 16f
        gravity = Gravity.CENTER
        setTextColor(ContextCompat.getColor(context, android.R.color.white))
    }
}