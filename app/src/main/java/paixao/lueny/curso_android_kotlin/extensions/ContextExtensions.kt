package paixao.lueny.curso_android_kotlin.extensions

import android.content.Context
import android.content.Intent
import android.widget.Toast

fun Context.goTo (clazz: Class<*>, intent: Intent.() -> Unit ={}){
    Intent(this,clazz)
        .apply {
            intent()
            startActivity(this)
        }
}

fun Context.toast(mensagem: String = "Falha na Autenticação" ){
    Toast.makeText(
        this,
        mensagem,
        Toast.LENGTH_SHORT
    ).show()
}