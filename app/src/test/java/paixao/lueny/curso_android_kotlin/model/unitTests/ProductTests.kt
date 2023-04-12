package paixao.lueny.curso_android_kotlin.model.unitTests

import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.junit.Test
import paixao.lueny.curso_android_kotlin.model.Product
import java.math.BigDecimal

class ProductTests {

    @Test
    fun `should retornar True when criar produto O valor certo EValido`() {

        //Arrange - Preparar o teste
        // Nesta etapa nós configuramos tudo o que é necessário para que o nosso teste possa rodar.

        val produtoValido = Product(
            name = "Banana",
            description = "Fruta",
            value = BigDecimal("6.99")
        )

        // Act - Rodar o teste
        //Esta etapa é onde rodamos de fato o nosso teste. Chamamos alguma função ou método que queremos por a prova.

        val valorValido = produtoValido.valorValido

        //Assert - Verificar assercoes
        //É onde verificamos se a operação realizada na etapa anterior (Act) surtiu o resultado esperado. Assim sabemos se o teste passa ou falha.

        valorValido.shouldBeTrue()
    }

    @Test
    fun `should retornar false when o valor for maior que cem reais`() {

        val produtoInvalido = Product(
            name = "Abacaxi",
            description = "Fruta",
            value = BigDecimal("105.30")
        )

        val valorValido = produtoInvalido.valorValido
        valorValido.shouldBeFalse()
    }

    @Test
    fun `should retornar false when o valor for menor ou igual a zero`() {

        val produtoComValorIgualAZero = Product(
            name = "Uva",
            description = "Fruta",
            value = BigDecimal("0.00")
        )
        val produtoComValorMenorQueZero = Product(
            name = "alface",
            description = "hortalica",
            value = BigDecimal("-3.00")
        )

        val valorIgualAZeroEValido= produtoComValorIgualAZero.valorValido
        val valorMenorQueZeroEValido = produtoComValorMenorQueZero.valorValido

        valorIgualAZeroEValido.shouldBeFalse()
        valorMenorQueZeroEValido.shouldBeFalse()
    }
}