package paixao.lueny.curso_android_kotlin.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import paixao.lueny.curso_android_kotlin.model.Product
import paixao.lueny.curso_android_kotlin.database.AppDatabase
import paixao.lueny.curso_android_kotlin.databinding.ActivityProductFormBinding
import paixao.lueny.curso_android_kotlin.imageProdutcs.DownloadImageForm
import paixao.lueny.curso_android_kotlin.extensions.tryLoadImage
import paixao.lueny.curso_android_kotlin.preferences.dataStore
import paixao.lueny.curso_android_kotlin.preferences.userLoggedPreferences
import java.math.BigDecimal

class ActivityProductForm : ActivityBase() {
    private val binding by lazy { ActivityProductFormBinding.inflate(layoutInflater) }
    private val productDao by lazy { AppDatabase.instance(this).productDao() }
    private val userDao by lazy { AppDatabase.instance(this).userDao() }
    private var url: String? = null
    private var productId = 0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = "Cadastrar produto"
        configureButtonSave()
        binding.activityProductsListImageview.setOnClickListener {
            DownloadImageForm(this)
                .showImage { imagem ->
                    url = imagem
                    binding.activityProductsListImageview.tryLoadImage(url)
                }
        }

        tryLoadProductId()
        lifecycleScope.launch {
            user
                .filterNotNull()
                .collect()
        }
    }

    private fun tryLoadProductId() {
//        lifecycleScope.launch {
            productId = intent.getLongExtra(ID_PRODUCT_KEY, 0L)
//        }
    }


    override fun onResume() {
        super.onResume()
        trySearchProductId()

    }

    private fun trySearchProductId() {
        lifecycleScope.launch {
            productDao.searchById(productId).collect{
                it?.let { productFound ->
                title = "Alterar Produto"
                fillFields(productFound)
            }
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

