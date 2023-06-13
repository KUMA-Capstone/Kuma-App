package com.capstone.kuma.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.capstone.kuma.R

class PasswordEditText : AppCompatEditText, View.OnTouchListener {
    private lateinit var clearButton: Drawable
    private lateinit var bgNormal: Drawable
    private lateinit var bgError: Drawable
    private var isError: Boolean = false
    private var errorText: String = ""

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        clearButton = ContextCompat.getDrawable(context, R.drawable.ic_close_24) as Drawable
        bgNormal = ContextCompat.getDrawable(context, R.drawable.bg_edit_text_normal) as Drawable
        bgError = ContextCompat.getDrawable(context, R.drawable.bg_edit_text_error) as Drawable
        setOnTouchListener(this)

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty()) showClearButton() else hideClearButton()
                if (isError) {
                    if (s.toString().isNotEmpty()) {
                        setErrorState(false)
                    }
                }
            }

            override fun afterTextChanged(s: Editable) {
                if (s.toString().isEmpty()) {
                    setErrorState(true, R.string.error_empty_password)
                } else if (s.length < 8) {
                    setErrorState(true, R.string.error_short_password)
                } else {
                    setErrorState(false)
                }
            }
        })
    }

    override fun onTouch(p0: View?, event: MotionEvent): Boolean {
        if (compoundDrawables[2] != null) {
            var clearButtonStart = 0.0f
            var clearButtonEnd = 0.0f
            var isClearButtonClicked = false
            if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                clearButtonEnd = (clearButton.intrinsicWidth + paddingStart).toFloat()
                when {
                    event.x < clearButtonEnd -> isClearButtonClicked = true
                }
            } else {
                clearButtonStart = (width - paddingEnd - clearButton.intrinsicWidth).toFloat()
                when {
                    event.x > clearButtonStart -> isClearButtonClicked = true
                }
            }
            if (isClearButtonClicked) {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        clearButton = ContextCompat.getDrawable(context, R.drawable.ic_close_24) as Drawable
                        showClearButton()
                        return true
                    }
                    MotionEvent.ACTION_UP -> {
                        clearButton = ContextCompat.getDrawable(context, R.drawable.ic_close_24) as Drawable
                        if (text != null) {
                            val isTextCleared = event.x > clearButtonStart && event.x < clearButtonEnd
                            if (isTextCleared) {
                                setErrorState(true, R.string.error_empty_password)
                            } else {
                                text?.clear()
                                setErrorState(false)
                            }
                        }
                        hideClearButton()
                        return true
                    }
                    else -> return false
                }
            } else return false
        }
        return false
    }

    private fun showClearButton() {
        setButtonDrawables(endOfTheText = clearButton)
    }

    private fun hideClearButton() {
        setButtonDrawables()
    }

    private fun setButtonDrawables(
        startOfTheText: Drawable? = null,
        topOfTheText: Drawable? = null,
        endOfTheText: Drawable? = null,
        bottomOfTheText: Drawable? = null
    ) {
        setCompoundDrawablesWithIntrinsicBounds(
            startOfTheText,
            topOfTheText,
            endOfTheText,
            bottomOfTheText
        )
    }

    private fun setErrorState(hasError: Boolean, errorMessageResId: Int = 0) {
        isError = hasError
        errorText = if (errorMessageResId != 0) {
            context.getString(errorMessageResId)
        } else {
            ""
        }

        background = if (hasError) {
            showErrorBackground()
            error = errorText
            bgError
        } else {
            showNormalBackground()
            error = null
            bgNormal
        }
    }

    private fun showErrorBackground() {
        val errorBackground = ContextCompat.getDrawable(context, R.drawable.bg_edit_text_error)
        background = errorBackground
    }

    private fun showNormalBackground() {
        val normalBackground = ContextCompat.getDrawable(context, R.drawable.bg_edit_text_normal)
        background = normalBackground
    }

    override fun onDraw(canvas: Canvas) {
        setPadding(30, 4, 40, 4)
        super.onDraw(canvas)
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
        background = if (isError) bgError else bgNormal
    }
}