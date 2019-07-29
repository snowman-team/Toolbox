package com.xueqiu.toolbox.ext

import com.google.gson.JsonElement
import com.google.gson.JsonObject

fun Boolean?.nullAsFalse(): Boolean {
    return this ?: false
}

fun Boolean?.nullAsTrue(): Boolean {
    return this ?: false
}

fun JsonObject.forEach(action: (String, JsonElement) -> Unit) {
    for (key in keySet()) {
        action(key, get(key))
    }
}

fun <T> MutableList<T>?.hasItem(block: (T) -> Boolean): Boolean {
    if (this.isNullOrEmpty()) return false
    return null != this.find {
        block(it)
    }
}

fun ByteArray.toHex(): String {
    return this.joinToString(separator = "") {
        "%02x".format(it)
    }
}

fun infiniteLoop(action: () -> Unit) {
    while (true) {
        action()
    }
}