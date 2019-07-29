package com.xueqiu.toolbox.ext

import android.net.Uri
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*

const val REGEX_IP = "\\b(?:\\d{1,3}\\.){3}\\d{1,3}\\b"

fun String?.toUri(): Uri? {
    if (this.isNullOrBlank()) {
        return null
    }
    return try {
        Uri.parse(this)
    } catch (e: Exception) {
        null
    }
}

fun String.toMD5(): String? {
    val md5: String
    val md: MessageDigest
    try {
        md = MessageDigest.getInstance("MD5")
        md.update(this.toByteArray(charset("UTF-8")))
        md5 = md.digest().toHex()
    } catch (e: Exception) {
        return null
    }
    return md5
}

fun String.toSHA1(): String? {
    val sha1: String
    val md: MessageDigest
    try {
        md = MessageDigest.getInstance("SHA1")
        md.update(this.toByteArray(charset("UTF-8")))
        sha1 = md.digest().toHex()
    } catch (e: Exception) {
        return null
    }
    return sha1
}

fun String?.isIP(): Boolean {
    if (null == this) return false
    return this.matches(Regex(REGEX_IP))
}

fun String.toCalendar(): Calendar? {
    val timePart: String
    var timeZonePart = ""
    when {
        this.length == 30 -> {
            val builder = StringBuilder(this.substring(0, 20))
            builder.append(this.substring(25))
            timePart = builder.toString()
            timeZonePart = this.substring(20, 25)
        }
        this.length == 34 -> { // example: Fri Feb 08 00:00:00 GMT+12:00 2013
            val builder = StringBuilder(this.substring(0, 20))
            builder.append(this.substring(29))
            timePart = builder.toString()
            timeZonePart = this.substring(23, 29)
        }
        this.length == 33 -> { // example: Fri Feb 08 00:00:00 GMT+0800 2013
            val builder = StringBuilder(this.substring(0, 20))
            builder.append(this.substring(28))
            timePart = builder.toString()
            timeZonePart = this.substring(23, 28)
        }
        this.length == 28 -> {
            val builder = StringBuilder(this.substring(0, 20))
            builder.append(this.substring(23))
            timePart = builder.toString()
            timeZonePart = this.substring(20, 24)
        }
        else -> timePart = this
    }

    val date = SimpleDateFormat("EEE MMM d HH:mm:ss yyyy", Locale.ENGLISH).parse(timePart)
            ?: return null
    val calendar = Calendar.getInstance()
    calendar.time = date
    calendar.timeZone = TimeZone.getTimeZone("GMT$timeZonePart")
    return calendar
}