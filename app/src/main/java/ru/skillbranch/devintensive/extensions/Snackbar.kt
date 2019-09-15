package ru.skillbranch.devintensive.extensions

import android.graphics.drawable.Drawable
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

fun Snackbar.setBackground(background: Drawable?): Snackbar {
    this.view.background = background
    return this
}

fun Snackbar.setTextColor(color: Int): Snackbar {
    this.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).setTextColor(color)
    return this
}