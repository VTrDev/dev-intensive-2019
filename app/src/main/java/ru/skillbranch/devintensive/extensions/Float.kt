package ru.skillbranch.devintensive.extensions

import android.content.res.Resources


val Float.toPx: Float
    get() = (this * Resources.getSystem().displayMetrics.density)

val Float.toDp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()