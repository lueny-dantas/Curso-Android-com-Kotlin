package paixao.lueny.curso_android_kotlin.extensions

import android.widget.ImageView
import coil.load
import paixao.lueny.curso_android_kotlin.R

fun ImageView.tryLoadImage(url: String? = null){
    load(url) {
        fallback(R.drawable.erro)
        error(R.drawable.erro)
        placeholder(R.drawable.placeholder)
    }
}