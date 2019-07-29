package com.xueqiu.toolbox.gson

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import com.xueqiu.toolbox.ext.toCalendar
import java.io.IOException
import java.util.*

object GsonManager {

    private var mGson: Gson? = null

    fun init(options: GsonOptions) {
        initGson(options)
    }

    fun getGson() = mGson ?: throw IllegalStateException("Please init gson manager first.")

    private fun initGson(options: GsonOptions) {

        val builder = GsonBuilder()
                .registerTypeAdapter(Date::class.java, DateTypeAdapter)
                .registerTypeAdapter(Calendar::class.java, CalendarTypeAdapter)
                .registerTypeAdapter(GregorianCalendar::class.java, CalendarTypeAdapter)
                .registerTypeAdapter(Double::class.javaObjectType, DoubleTypeAdapter)
                .registerTypeAdapter(Double::class.java, DoubleTypeAdapter)
                .registerTypeAdapter(Int::class.javaObjectType, IntegerTypeAdapter)
                .registerTypeAdapter(Int::class.java, IntegerTypeAdapter)

        if (options.enableLowercaseWithUnderscores) {
            builder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        }
        if (options.serializeSpecialFloatingPointValues) {
            builder.serializeSpecialFloatingPointValues()
        }

        mGson = builder.create()
    }

    private val DateTypeAdapter = object : TypeAdapter<Date>() {

        @Throws(IOException::class)
        override fun write(out: JsonWriter, value: Date?) {
            out.value(value?.time ?: 0)
        }

        @Throws(IOException::class)
        override fun read(`in`: JsonReader): Date? {
            if (`in`.peek() == JsonToken.NULL) {
                `in`.nextNull()
                return null
            }
            try {
                val result = `in`.nextString()
                if ("" == result || "null".equals(result, ignoreCase = true)) {
                    return null
                }
                return if (result.matches("^[0-9]+$".toRegex())) {
                    Date(result.toLong())
                } else {
                    result.toCalendar()?.time
                }
            } catch (e: Exception) {
                return null
            }

        }
    }

    private val CalendarTypeAdapter = object : TypeAdapter<Calendar>() {
        @Throws(IOException::class)
        override fun write(out: JsonWriter, value: Calendar?) {
            out.value(value?.time?.toString())
        }

        @Throws(IOException::class)
        override fun read(`in`: JsonReader): Calendar? {
            if (`in`.peek() == JsonToken.NULL) {
                `in`.nextNull()
                return null
            }

            return try {
                val result = `in`.nextString()
                if ("" == result
                        || "null".equals(result, ignoreCase = true)
                        || "0" == result) {
                    null
                } else result.toCalendar()

            } catch (e: Exception) {
                null
            }

        }
    }

    private val DoubleTypeAdapter = object : TypeAdapter<Number>() {

        @Throws(IOException::class)
        override fun write(out: JsonWriter, value: Number) {
            out.value(value)
        }

        @Throws(IOException::class)
        override fun read(`in`: JsonReader): Number? {
            if (`in`.peek() == JsonToken.NULL) {
                `in`.nextNull()
                return null
            }
            return try {
                val result = `in`.nextString()
                if ("" == result || "null".equals(result, ignoreCase = true)) {
                    null
                } else {
                    result.toDouble()
                }
            } catch (e: Exception) {
                null
            }

        }
    }

    private val IntegerTypeAdapter = object : TypeAdapter<Number>() {
        @Throws(IOException::class)
        override fun write(out: JsonWriter, value: Number) {
            out.value(value)
        }

        @Throws(IOException::class)
        override fun read(`in`: JsonReader): Number? {
            if (`in`.peek() == JsonToken.NULL) {
                `in`.nextNull()
                return null
            }
            return try {
                val result = `in`.nextString()
                if ("" == result
                        || "null".equals(result, ignoreCase = true)
                        || "0.0".equals(result, ignoreCase = true)) {
                    null
                } else {
                    result.toInt()
                }
            } catch (e: Exception) {
                null
            }
        }
    }

}