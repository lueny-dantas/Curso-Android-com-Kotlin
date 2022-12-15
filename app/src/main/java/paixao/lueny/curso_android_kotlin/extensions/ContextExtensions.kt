package paixao.lueny.curso_android_kotlin.extensions

import android.content.Context
import android.content.Intent

fun Context.goTo (clazz: Class<*>, intent: Intent.() -> Unit ={}){
    Intent(this,clazz)
        .apply {
            intent()
            startActivity(this)
        }
}