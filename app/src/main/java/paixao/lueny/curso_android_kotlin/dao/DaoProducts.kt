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
            Product(
                name = "Abacaxi",
                description = "Fruta Tropical",
                value = BigDecimal("3.99"),
                image = "https://conteudo.imguol.com.br/c/entretenimento/04/2017/12/11/abacaxi-1513012505452_v2_900x506.jpg.webp"
            ),
            Product(
                name = "Maça",
                description = " A maçã é o pseudofruto pomáceo da macieira (Malus domestica), árvore da família Rosaceae. É um dos pseudofrutos de árvore mais cultivados, e o mais conhecido dos muitos membros do género Malus que são usados \u200B\u200Bpelos seres humanos.",
                value = BigDecimal("5.60"),
                image = "https://conteudo.imguol.com.br/c/entretenimento/32/2018/01/18/maca-1516308281068_v2_900x506.jpg.webp"
            ),

            Product(
                name = "Morango",
                description = "O morango é uma fruta vermelha, cuja origem é a Europa. Produzida pelo morangueiro, é um fruto rasteiro. Existem várias espécies de morango, sendo a fragaria a mais comum e cultivada em várias partes do mundo. É uma fruta pouco calórica, apresentando cerca de 32 kcal por 100 gramas de morango.",
                value = BigDecimal("12.89"),
                image = "https://images.pexels.com/photos/46174/strawberries-berries-fruit-freshness-46174.jpeg"
            ),
        )


    }
}


