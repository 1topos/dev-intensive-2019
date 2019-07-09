package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*
import kotlin.String
import kotlin.math.abs

fun Date.format(pattern: kotlin.String = "HH:mm:ss dd.MM.yy") : kotlin.String {
    val formatter = SimpleDateFormat(pattern, Locale("ru"))
    return formatter.format(this)
}

fun Date.add(value: Int, units: TimeUnits) : Date {
    /**this.time += when(units) {
     *  TimeUnits.SECOND -> 1000
     *  TimeUnits.MINUTE -> 1000 * 60
     *  TimeUnits.HOUR -> 1000 * 60 * 60
     *  TimeUnits.DAY -> 1000 * 60 * 60 * 24}
     */
    this.time += units.time * value
    return this
}

fun Date.humanizeDiff(date: Date = Date()) : String {
    val diff = abs(date.time - this.time)
    val isFuture = date.time < this.time

    return when (diff) {
        in 0 .. TimeUnits.SECOND.time * 1 -> "только что"
        in TimeUnits.SECOND.time .. TimeUnits.SECOND.time * 45 -> if(isFuture) "через несколько секунд" else "несколько секунд назад"
        in TimeUnits.SECOND.time * 45 .. TimeUnits.SECOND.time * 75 -> if(isFuture) "через минуту" else "минуту назад"
        in TimeUnits.SECOND.time * 75 .. TimeUnits.MINUTE.time * 45 -> {
            return if (isFuture)
                "через ${TimeUnits.MINUTE.plural((diff / TimeUnits.MINUTE.time).toInt())}"
            else
                "${TimeUnits.MINUTE.plural((diff / TimeUnits.MINUTE.time).toInt())} назад"
        }
        in TimeUnits.MINUTE.time * 45 .. TimeUnits.MINUTE.time * 75 -> if(isFuture) "через час" else "час назад"
        in TimeUnits.MINUTE.time * 75 .. TimeUnits.HOUR.time * 22 -> {
            return if (isFuture)
                "через ${TimeUnits.HOUR.plural((diff / TimeUnits.HOUR.time).toInt())}"
            else
                "${TimeUnits.HOUR.plural((diff / TimeUnits.HOUR.time).toInt())} назад"
        }
        in TimeUnits.HOUR.time * 22 .. TimeUnits.HOUR.time * 26 -> if(isFuture) "через день" else "день назад"
        in TimeUnits.HOUR.time * 26 .. TimeUnits.DAY.time * 360 -> {
            return if (isFuture)
                "через ${TimeUnits.DAY.plural((diff / TimeUnits.DAY.time).toInt())}"
            else
                "${TimeUnits.DAY.plural((diff / TimeUnits.DAY.time).toInt())} назад"
        }
        else -> if(isFuture) "более чем через год" else "более года назад"
    }
}

enum class TimeUnits(val time: Long) {
    SECOND(1000),
    MINUTE(SECOND.time * 60),
    HOUR(MINUTE.time * 60),
    DAY(HOUR.time * 24);

    fun plural(value: Int) : String {
        val lastDigit = value % 10
        val isFirstDigitOne = (value / 10 % 10) == 1
        return when(this) {
            SECOND -> when(lastDigit) {
                1 -> if(isFirstDigitOne) "$value секунд" else "$value секунду"
                in 2 .. 4 -> if(isFirstDigitOne) "$value секунд" else "$value секунды"
                else -> "$value секунд"
            }
            MINUTE -> when(lastDigit) {
                1 -> if(isFirstDigitOne) "$value минут" else "$value минуту"
                in 2 .. 4 -> if(isFirstDigitOne) "$value минут" else "$value минуты"
                else -> "$value минут"
            }
            HOUR -> when (lastDigit) {
                1 -> if(isFirstDigitOne) "$value часов" else "$value час"
                in 2 .. 4 -> if(isFirstDigitOne) "$value часов" else "$value часа"
                else -> "$value часов"
            }
            else -> when (lastDigit) {
                1 -> if(isFirstDigitOne) "$value дней" else "$value день"
                in 2 .. 4 -> if(isFirstDigitOne) "$value дней" else "$value дня"
                else -> "$value дней"
            }
        }
    }
}