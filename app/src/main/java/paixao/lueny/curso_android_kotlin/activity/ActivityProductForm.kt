package paixao.lueny.curso_android_kotlin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import coil.load
import paixao.lueny.curso_android_kotlin.Produto.Product
import paixao.lueny.curso_android_kotlin.R
import paixao.lueny.curso_android_kotlin.dao.DaoProducts
import paixao.lueny.curso_android_kotlin.databinding.ActivityProductFormBinding
import paixao.lueny.curso_android_kotlin.databinding.ImageFormBinding
import paixao.lueny.curso_android_kotlin.dialog.ImageFormDialog
import paixao.lueny.curso_android_kotlin.extensions.tryLoadImage
import java.math.BigDecimal

class ActivityProductForm : AppCompatActivity() {
    private val binding by lazy {
        ActivityProductFormBinding.inflate(layoutInflater)
    }
    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = "Cadastrar produto"
        configureButtonSave()
        binding.activityProductsListImageview.setOnClickListener {
            ImageFormDialog(this)
                .show{ imagem ->
                    url = imagem
                    binding.activityProductsListImageview.tryLoadImage(url)
                }
        }
    }

    private fun configureButtonSave() {
        val salveButton = binding.activityProductsListSaveButton
        val dao = DaoProducts()
        salveButton.setOnClickListener {
            val createdProduct = createProduct()
            dao.add(createdProduct)
            finish()
        }
    }

    private fun createProduct(): Product {
        val nameField = binding.activityProductsListName
        val name = nameField.text.toString()
        val descriptionField = binding.activityProductsListDescription
        val description = descriptionField.text.toString()
        val valueField = binding.activityProductsListValue
        val textValue = valueField.text.toString()
        val value = if (textValue.isBlank()) {
            BigDecimal.ZERO
        } else {
            BigDecimal(textValue)
        }
        return Product(
            name = name,
            description = description,
            value = value,
            image = url
        )
    }
}

