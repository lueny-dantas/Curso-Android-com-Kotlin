package paixao.lueny.curso_android_kotlin.dao

import paixao.lueny.curso_android_kotlin.Produto.Product

class DaoProducts {

    fun add(product: Product) {
        products.add(product)
    }

    fun searchAll(): List<Product> {
        return products.toList()
    }

    companion object {
        private val products = mutableListOf<Product>()
    }
}


