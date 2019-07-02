package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern: String="HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time

    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }
    this.time = time
    return this
}

fun Date.humanizeDiff(date: Date = Date()): String { // ДЗ 1:16, 1:22
    val diff = this.time - date.time

    return when (diff) {
        in -SECOND .. SECOND -> "только что"
        in SECOND + 1 .. 45 * SECOND -> "несколько секунд назад"
        in 45 * SECOND + 1 .. 75 * SECOND -> "минуту назад"
        in 75 * SECOND + 1 .. 45 * MINUTE -> "${humanizeTimeUnits(diff / MINUTE, TimeUnits.MINUTE)} назад"
        in 45 * MINUTE + 1 .. 75 * MINUTE -> "час назад"
        in 75 * MINUTE + 1 .. 22 * HOUR -> "${humanizeTimeUnits(diff / HOUR, TimeUnits.HOUR)} назад"
        in 22 * HOUR + 1 .. 26 * HOUR -> "день назад"
        in 26 * HOUR + 1 .. 360 * DAY -> "${humanizeTimeUnits(diff / DAY, TimeUnits.DAY)} назад"
        in 360 * DAY .. Long.MAX_VALUE -> "более года назад"
        else -> "неизвестно"
    }
}

fun Date.humanizeTimeUnits(value: Long, units: TimeUnits = TimeUnits.SECOND): String {

    val preLastDigit = value % 100 / 10

    if (preLastDigit == 1L) return when (units) {
        TimeUnits.SECOND -> "$value секунд"
        TimeUnits.MINUTE -> "$value минут"
        TimeUnits.HOUR -> "$value часов"
        TimeUnits.DAY -> "$value дней"
    }

    when (value % 10) {
        1L -> return when (units) {
            TimeUnits.SECOND -> "$value секунду"
            TimeUnits.MINUTE -> "$value день"
            TimeUnits.HOUR -> "$value час"
            TimeUnits.DAY -> "$value день"
        }
        in 2..4 -> return when (units) {
            TimeUnits.SECOND -> "$value секунды"
            TimeUnits.MINUTE -> "$value минуты"
            TimeUnits.HOUR -> "$value часа"
            TimeUnits.DAY -> "$value дня"
        }
        else -> return when (units) {
            TimeUnits.SECOND -> "$value секунд"
            TimeUnits.MINUTE -> "$value минут"
            TimeUnits.HOUR -> "$value часов"
            TimeUnits.DAY -> "$value дней"
        }
    }
}

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY
}