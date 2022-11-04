package paixao.lueny.curso_android_kotlin.dialog

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import paixao.lueny.curso_android_kotlin.databinding.ImageFormBinding
import paixao.lueny.curso_android_kotlin.extensions.tryLoadImage

class ImageFormDialog(private val context: Context) {

    fun show(uploadedImage: (image:String) -> Unit) {
        val binding = ImageFormBinding.inflate(LayoutInflater.from(context))
        binding.imageFormButtonLoad.setOnClickListener {
            val url = binding.imageFormUrl.text.toString()
            binding.imageFormImageview.tryLoadImage(url)

        }

        AlertDialog.Builder(context)
            .setView(binding.root)
            .setPositiveButton("confirmar")
            { _, _ ->
                val url = binding.imageFormUrl.text.toString()
                Log.i("ImageFormDialog", "show: $url")
                 uploadedImage(url)
            }
            .setNegativeButton("cancelar")
            { _, _ -> }

            .show()

    }
}