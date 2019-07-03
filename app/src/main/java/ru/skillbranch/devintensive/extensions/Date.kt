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

    return when (val diff = date.time - this.time) {

        in -SECOND .. SECOND -> "только что"

        in SECOND + 1 .. 45 * SECOND -> "несколько секунд назад"
        in 45 * SECOND + 1 .. 75 * SECOND -> "минуту назад"
        in 75 * SECOND + 1 .. 45 * MINUTE -> "${TimeUnits.MINUTE.plural(diff.toInt() / MINUTE.toInt())} назад"
        in 45 * MINUTE + 1 .. 75 * MINUTE -> "час назад"
        in 75 * MINUTE + 1 .. 22 * HOUR -> "${TimeUnits.HOUR.plural(diff.toInt() / HOUR.toInt())} назад"
        in 22 * HOUR + 1 .. 26 * HOUR -> "день назад"
        in 26 * HOUR + 1 .. 360 * DAY -> "${TimeUnits.DAY.plural(diff.toInt() / DAY.toInt())} назад"
        in 360 * DAY + 1 .. Long.MAX_VALUE -> "более года назад"

        in -45 * SECOND until -SECOND - 1 -> "через несколько секунд"
        in -75 * SECOND until -45 * SECOND - 1 -> "через минуту"
        in -45 * MINUTE until -75 * SECOND - 1 -> "через ${TimeUnits.MINUTE.plural(-diff.toInt() / MINUTE.toInt())}"
        in -75 * MINUTE until -45 * MINUTE - 1 -> "через час"
        in -22 * HOUR until -75 * MINUTE - 1 -> "через ${TimeUnits.HOUR.plural(-diff.toInt() / HOUR.toInt())}"
        in -26 * HOUR until -22 * HOUR - 1 -> "через день"
        in -360 * DAY until -26 * HOUR - 1 -> "через ${TimeUnits.DAY.plural(-diff.toInt() / DAY.toInt())}"
        in Long.MIN_VALUE until -360 * DAY - 1 -> "более чем через год"

        else -> "неизвестно"
    }
}

/*fun Date.humanizeTimeUnits(value: Long, units: TimeUnits = TimeUnits.SECOND): String {

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
}*/

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY;

    fun plural(value: Int): String {

        val units = this

        val preLastDigit = value % 100 / 10

        if (preLastDigit == 1) return when (units) {
            SECOND -> "$value секунд"
            MINUTE -> "$value минут"
            HOUR -> "$value часов"
            DAY -> "$value дней"
        }

        when (value % 10) {
            1 -> return when (units) {
                SECOND -> "$value секунду"
                MINUTE -> "$value минуту"
                HOUR -> "$value час"
                DAY -> "$value день"
            }
            in 2..4 -> return when (units) {
                SECOND -> "$value секунды"
                MINUTE -> "$value минуты"
                HOUR -> "$value часа"
                DAY -> "$value дня"
            }
            else -> return when (units) {
                SECOND -> "$value секунд"
                MINUTE -> "$value минут"
                HOUR -> "$value часов"
                DAY -> "$value дней"
            }
        }
    }
}