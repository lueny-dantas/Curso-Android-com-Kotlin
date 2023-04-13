package paixao.lueny.curso_android_kotlin.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import paixao.lueny.curso_android_kotlin.R
import paixao.lueny.curso_android_kotlin.database.AppDatabase
import paixao.lueny.curso_android_kotlin.databinding.ActivityProductDetailsBinding
import paixao.lueny.curso_android_kotlin.extensions.currencyFormatting
import paixao.lueny.curso_android_kotlin.extensions.tryLoadImage
import paixao.lueny.curso_android_kotlin.model.Product


class ActivityProductDetails : AppCompatActivity() {

    private var productId: Long = 0L
    private var product: Product? = null
    private val binding by lazy { ActivityProductDetailsBinding.inflate(layoutInflater) }
    private val productDao by lazy { AppDatabase.instance(context = this).productDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tryLoadProduct()
    }

    override fun onResume() {
        super.onResume()
        searchProductDatabase()
    }

    private fun searchProductDatabase() {
        lifecycleScope.launch {
            productDao.searchById(productId).collect { productFound ->
                product = productFound
                product?.let {
                    fillFields(it)
                } ?: finish()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_product_details, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_product_details_remove -> {
                product?.let {
                    lifecycleScope.launch {
                        productDao.remove(it)
                        finish()
                    }
                }

            }
            R.id.menu_product_details_edit -> {
                Intent(this, ActivityProductForm::class.java).apply {
                    putExtra(ID_PRODUCT_KEY, productId)
                    startActivity(this)

                }
            }
        }
        return super.onContextItemSelected(item)
    }

    private fun tryLoadProduct() {
        productId = intent.getLongExtra(ID_PRODUCT_KEY, 0L)
    }

    private fun fillFields(productLoaded: Product) {
        with(binding) {
            activityProductDetailsImage.tryLoadImage(productLoaded.image)
            acttivityProductDetailsDescription.text = productLoaded.description
            activityProductDetailsValue.text = productLoaded.value.currencyFormatting()
            activityProductDetailsName.text = productLoaded.name

            }
        }
    }


