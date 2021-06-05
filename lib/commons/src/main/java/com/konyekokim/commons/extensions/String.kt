package com.konyekokim.commons.extensions

import java.math.RoundingMode

fun Double.to1dpString(): String =
this.toBigDecimal().setScale(1, RoundingMode.UP)
    .toDouble().toString()