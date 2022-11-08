package paixao.lueny.curso_android_kotlin.extensions

import android.widget.ImageView
import coil.load
import paixao.lueny.curso_android_kotlin.R

fun ImageView.tryLoadImage(
    url: String? = null,
    fallback: Int = R.drawable.imagem_padrao
) {
    load(url) {
        fallback(fallback)
        error(R.drawable.erro)
        placeholder(R.drawable.placeholder)
    }
}