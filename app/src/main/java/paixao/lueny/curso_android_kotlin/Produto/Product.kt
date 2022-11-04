package paixao.lueny.curso_android_kotlin.Produto

import java.math.BigDecimal

 data class Product(
    val name: String,
    val description: String,
    val value: BigDecimal,
    val image: String? = null
)

