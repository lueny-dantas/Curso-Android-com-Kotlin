package paixao.lueny.curso_android_kotlin.model

import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.junit.Test

class UserTests {

    @Test
    fun `should retornar true when os dados do usuario estao certos`(){

        //Arrange - Preparar o teste
        // Nesta etapa nós configuramos tudo o que é necessário para que o nosso teste possa rodar.

        val usuarioValido = User(
            id = "usuario",
            password = "senhaforte123"
        )

        // Act - Rodar o teste
        //Esta etapa é onde rodamos de fato o nosso teste. Chamamos alguma função ou método que queremos por a prova.

        val resultado = usuarioValido.cadastroValido

        //Assert - Verificar assercoes
        //É onde verificamos se a operação realizada na etapa anterior (Act) surtiu o resultado esperado. Assim sabemos se o teste passa ou falha.

        resultado.shouldBeTrue()
    }

    @Test
    fun `should retornar false when a senha do usuario estiver errada contendo menos de 5 caracteres`() {

        val usuarioInvalido = User(
            id = "usuario",
            password = "1234"
        )

        val resultado = usuarioInvalido.cadastroValido
        resultado.shouldBeFalse()
    }

}