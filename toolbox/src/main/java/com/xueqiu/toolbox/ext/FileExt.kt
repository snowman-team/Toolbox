package com.xueqiu.toolbox.ext

import java.io.File
import java.io.InputStream

// return kb
fun File?.fileSize(): Long {
    if (null == this) return 0
    var size: Long = 0
    if (this.isDirectory) {
        val files = this.listFiles() ?: return 0
        for (file in files) {
            size += file.fileSize()
        }
    } else {
        size += this.length()
    }
    return size
}

fun File?.write(data: ByteArray, append: Boolean = false): Boolean {
    if (null == this) return false
    if (append) {
        this.appendBytes(data)
    } else {
        this.writeBytes(data)
    }
    return true
}

fun File?.write(data: String, append: Boolean = false): Boolean {
    if (null == this) return false
    return this.write(data.toByteArray(), append)
}

fun File?.write(stream: InputStream, append: Boolean = false): Boolean {
    if (null == this) return false
    return this.write(stream.readBytes(), append)
}

fun File?.write(file: File, append: Boolean = false): Boolean {
    if (null == this) return false
    return try {
        file.copyTo(this, !append)
        true
    } catch (e: Exception) {
        false
    }
}

fun File?.clear() {
    if (null == this) return
    if (!this.exists()) {
        return
    }
    if (this.isDirectory) {
        val files = this.listFiles() ?: return
        files.forEach {
            it.clear()
        }
    }
    this.delete()
}