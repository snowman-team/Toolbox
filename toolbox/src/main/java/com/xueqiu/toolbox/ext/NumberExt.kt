package com.xueqiu.toolbox.ext

infix fun Int.equalCast(value: Long): Boolean = this.toLong() == value

infix fun Long.equalCast(value: Int): Boolean = this == value.toLong()

infix fun Int.equalCast(value: Float): Boolean = this.toFloat() == value

infix fun Float.equalCast(value: Int): Boolean = this == value.toFloat()

infix fun Double.highPlus(value: Double): Double = (this.toBigDecimal() + value.toBigDecimal()).toDouble()

infix fun Double.highSub(value: Double): Double = (this.toBigDecimal() - value.toBigDecimal()).toDouble()