package com.xueqiu.toolbox.store

import android.annotation.SuppressLint
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

abstract class BaseStorage<T>(private val key: String, private val defaultValue: T) : ReadWriteProperty<Any?, T> {

    abstract fun getStorage(): SharedPreferences

    @Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return with(getStorage()) {
            when (defaultValue) {
                is Long -> getLong(key, defaultValue)
                is Int -> getInt(key, defaultValue)
                is String -> getString(key, defaultValue)
                is Boolean -> getBoolean(key, defaultValue)
                is Float -> getFloat(key, defaultValue)
                is Double -> Double.fromBits(getLong(key, defaultValue.toRawBits()))
                else -> throw IllegalArgumentException("This type can be saved into Preferences")
            }
        } as T
    }

    @SuppressLint("CommitPrefEdits")
    @Suppress("UNCHECKED_CAST")
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        with(getStorage().edit()) {
            when (value) {
                is Long -> putLong(key, value)
                is String -> putString(key, value)
                is Int -> putInt(key, value)
                is Boolean -> putBoolean(key, value)
                is Float -> putFloat(key, value)
                is Double -> putLong(key, value.toRawBits())
                else -> throw IllegalArgumentException("This type can be saved into Preferences")
            }.apply()
        }
    }
}