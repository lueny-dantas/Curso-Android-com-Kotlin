package paixao.lueny.curso_android_kotlin.dao

import paixao.lueny.curso_android_kotlin.Produto.Product
import java.math.BigDecimal

class DaoProducts {

    fun add(product: Product) {
        products.add(product)
    }

    fun searchAll(): List<Product> {
        return products.toList()
    }

    companion object {
        private val products = mutableListOf<Product>(
            Product(name = "Fruta",
            description = "Abacaxi",
            value = BigDecimal("5.99")
            )
        )
    }
}


