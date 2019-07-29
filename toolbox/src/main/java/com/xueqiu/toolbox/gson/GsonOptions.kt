package com.xueqiu.toolbox.gson

class GsonOptions {

    var enableLowercaseWithUnderscores: Boolean = true
        private set

    var serializeSpecialFloatingPointValues: Boolean = true
        private set

    fun setLowercaseWithUnderscores(boolean: Boolean): GsonOptions {
        enableLowercaseWithUnderscores = boolean
        return this
    }

    fun setSerializeSpecialFloatingPointValues(boolean: Boolean): GsonOptions {
        serializeSpecialFloatingPointValues = boolean
        return this
    }

}