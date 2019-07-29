package com.xueqiu.toolbox.ext

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import java.util.*

const val KB: Long = 1024
const val MB: Long = 1024 * KB
const val GB: Long = 1024 * MB

fun Context.runOnMainThread(action: Context.() -> Unit) {
    if (Looper.getMainLooper().thread == Thread.currentThread()) {
        action()
    } else {
        Handler(Looper.getMainLooper()).post { action() }
    }
}

fun Context?.isActivityFinished(): Boolean {
    this ?: return false
    return if (this is Activity) {
        this.isActivityFinish()
    } else {
        true
    }
}

fun Context?.isActivityActive(): Boolean {
    this ?: return false
    return if (this is Activity) {
        !this.isActivityFinish()
    } else {
        false
    }
}

fun Activity.isActivityFinish(): Boolean = this.isFinishing || this.isDestroyed

fun Context?.getProcessName(): String? {
    this ?: return null
    return try {
        val pid = android.os.Process.myPid()
        val mActivityManager = this.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (appProcess in mActivityManager.runningAppProcesses) {
            if (appProcess.pid == pid) {
                return appProcess.processName
            }
        }
        null
    } catch (e: Exception) {
        null
    }
}

fun Context?.isOnMainProcess(): Boolean {
    this ?: return false
    val process = this.getProcessName()
    return process == null || process == this.packageName
}

fun Context?.getAppName(): String? {
    this ?: return null
    return try {
        resources.getString(packageManager.getPackageInfo(packageName, 0).applicationInfo.labelRes)
    } catch (e: PackageManager.NameNotFoundException) {
        null
    }
}

fun Context?.getDisplayDensity(): String? {
    this ?: return null
    val density = resources.displayMetrics.density
    return when {
        density <= 1 -> "mdpi"
        density < 2 -> "hdpi"
        density < 2.5 -> "xhdpi"
        density <= 3 -> "xxhdpi"
        else -> "xxxhdpi"
    }
}

fun Context?.getContextLanguage(): Locale? {
    this ?: return null
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        resources.configuration.locales.get(0)
    } else {
        resources.configuration.locale
    }
}