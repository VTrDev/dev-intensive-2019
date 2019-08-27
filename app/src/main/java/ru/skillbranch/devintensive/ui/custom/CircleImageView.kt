package ru.skillbranch.devintensive.ui.custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.Dimension
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import ru.skillbranch.devintensive.R
import android.graphics.Bitmap
import ru.skillbranch.devintensive.extensions.toDp
import ru.skillbranch.devintensive.extensions.toPx

class CircleImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr ) {
    companion object {
        private const val DEFAULT_BORDER_COLOR = Color.WHITE
        private const val DEFAULT_BORDER_WIDTH = 2f
    }

    private var borderColor = DEFAULT_BORDER_COLOR
    private var borderWidth = DEFAULT_BORDER_WIDTH.toPx

    private var bitmapPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView)
            borderColor = a.getColor(R.styleable.CircleImageView_cv_borderColor, DEFAULT_BORDER_COLOR)
            borderWidth = a.getDimension(R.styleable.CircleImageView_cv_borderWidth, DEFAULT_BORDER_WIDTH.toPx)
            a.recycle()

            // Log.d("M_CircleImageView", "$borderColor - $borderWidth")
        }
    }

    @Dimension fun getBorderWidth(): Int = borderWidth.toDp

    fun setBorderWidth(@Dimension dp: Int) {
        borderWidth = dp.toFloat().toPx
        invalidate()
    }

    fun getBorderColor(): Int = borderColor

    fun setBorderColor(hex: String) {
        borderColor = Color.parseColor(hex)
        invalidate()
    }

    fun setBorderColor(@ColorRes colorId: Int) {
        borderColor = ResourcesCompat.getColor(resources, colorId, null)
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {

        paintSetup()

        val r = width / 2f

        with (canvas!!) {
            drawCircle(r, r, r, bitmapPaint)
            drawCircle(r, r, r - borderWidth / 2f, borderPaint)
        }
    }

    private fun paintSetup() {
        borderPaint.color = borderColor
        borderPaint.style = Paint.Style.STROKE
        borderPaint.strokeWidth = borderWidth

        if (drawable != null) {
            var bitmap = drawable.toBitmap()
            bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false)
            bitmapPaint.shader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        } else {
            bitmapPaint.color = Color.TRANSPARENT
        }
    }

}