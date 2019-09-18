package ru.skillbranch.devintensive.ui.custom

import android.graphics.*
import android.graphics.drawable.Drawable
import android.graphics.RectF

class AvatarPlaceholder(
    private var avatarText: String? = "",
    textColor: Int = Color.WHITE,
    backgroundColor: Int = Color.BLUE,
    val width: Int = 10,
    val height: Int = 10,
    val isRandomBackgroundColor: Boolean = false
) : Drawable() {

    private val textPaint: Paint = Paint()
    private val backgroundPaint: Paint = Paint()
    private var placeholderBounds: RectF = RectF()

    private var textStartXPoint = 0f
    private var textStartYPoint = 0f
    private var textSizePercentage = 40f

    private val mBackgroundColors = arrayOf(
        "#7BC862",
        "#E17076",
        "#FAA774",
        "#6EC9CB",
        "#65AADD",
        "#A695E7",
        "#EE7AAE"
    )

    init {
        avatarText = avatarText ?: "??"

        textPaint.isAntiAlias = true
        textPaint.color = textColor
        textPaint.typeface = Typeface.create("sans-serif-light", Typeface.BOLD)

        backgroundPaint.isAntiAlias = true
        backgroundPaint.style = Paint.Style.FILL
        backgroundPaint.color =
            if (!isRandomBackgroundColor) backgroundColor
            else calculateColor(avatarText!!)
    }

    override fun draw(canvas: Canvas) {
        placeholderBounds = RectF(0f, 0f, bounds.width().toFloat(), bounds.width().toFloat())

        textPaint.textSize = calculateTextSize()
        textStartXPoint = calculateTextStartXPoint()
        textStartYPoint = calculateTextStartYPoint()

        canvas.drawRect(placeholderBounds, backgroundPaint)
        canvas.drawText(avatarText!!, textStartXPoint, textStartYPoint, textPaint)
    }

    override fun setAlpha(alpha: Int) {
        textPaint.alpha = alpha
        backgroundPaint.alpha = alpha
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        textPaint.colorFilter = colorFilter
        backgroundPaint.colorFilter = colorFilter
    }

    private fun calculateColor(str: String): Int {
        var sum = 0
        for (char in str.toCharArray()) {
            sum += char.toInt()
        }

        return Color.parseColor(
            mBackgroundColors[sum % mBackgroundColors.size]
        )
    }

    private fun calculateTextStartXPoint(): Float {
        val stringWidth = textPaint.measureText(avatarText)
        return bounds.width() / 2f - stringWidth / 2f
    }

    private fun calculateTextStartYPoint(): Float {
        return bounds.height() / 2f - (textPaint.ascent() + textPaint.descent()) / 2f
    }

    private fun calculateTextSize(): Float {
        if (textSizePercentage < 0 || textSizePercentage > 100) {
            textSizePercentage
        }
        return bounds.height() * textSizePercentage / 100
    }

    override fun getIntrinsicWidth(): Int {
        return width
    }

    override fun getIntrinsicHeight(): Int {
        return height
    }
}