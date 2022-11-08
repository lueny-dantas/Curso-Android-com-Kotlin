package paixao.lueny.curso_android_kotlin.extensions

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

fun BigDecimal.currencyFormatting(): String{
    val formatter:NumberFormat = NumberFormat
        .getCurrencyInstance(Locale("pt","br"))
    return formatter.format(this)
}