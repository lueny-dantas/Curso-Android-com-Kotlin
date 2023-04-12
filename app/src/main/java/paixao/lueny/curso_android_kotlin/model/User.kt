package paixao.lueny.curso_android_kotlin.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey var id: String,
    var name: String = "",
    var password: String = ""
){
    @Ignore
    private val senhaValida = password.count() >= 5

    @Ignore
    val cadastroValido = senhaValida
}



