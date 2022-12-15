package paixao.lueny.curso_android_kotlin.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import paixao.lueny.curso_android_kotlin.model.Product
import paixao.lueny.curso_android_kotlin.database.AppDatabase
import paixao.lueny.curso_android_kotlin.databinding.ActivityProductFormBinding
import paixao.lueny.curso_android_kotlin.dialog.ImageFormDialog
import paixao.lueny.curso_android_kotlin.extensions.tryLoadImage
import java.math.BigDecimal

class ActivityProductForm : AppCompatActivity() {
    private val binding by lazy { ActivityProductFormBinding.inflate(layoutInflater) }
    private val productDao by lazy { AppDatabase.instance(this).productDao() }
    private var url: String? = null
    private var productId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = "Cadastrar produto"
        configureButtonSave()
        binding.activityProductsListImageview.setOnClickListener {
            ImageFormDialog(this)
                .show { imagem ->
                    url = imagem
                    binding.activityProductsListImageview.tryLoadImage(url)
                }
        }
        tryLoadProductId()
    }

    private fun tryLoadProductId() {
        lifecycleScope.launch {
            productId = intent.getLongExtra(ID_PRODUCT_KEY, 0L)
        }
    }


    override fun onResume() {
        super.onResume()
        trySearchProductId()

    }

    private fun trySearchProductId() {
        lifecycleScope.launch {
            productDao.searchById(productId)?.let {
                    title = "Alterar Produto"
                    fillFields(it)
                }
            }
        }

    private fun fillFields(product: Product) {
        url = product.image
        binding.activityProductsListImageview.tryLoadImage(product.image)
        binding.activityProductsListName.setText(product.name)
        binding.activityProductsListDescription.setText(product.description)
        binding.activityProductsListValue.setText(product.value.toPlainString())
    }

    private fun configureButtonSave() {
        val salveButton = binding.activityProductsListSaveButton
        salveButton.setOnClickListener {
            val newProduct = createProduct()
            lifecycleScope.launch {
                productDao.save(newProduct)
                finish()
            }
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
            id = productId,
            name = name,
            description = description,
            value = value,
            image = url
        )
    }
}

