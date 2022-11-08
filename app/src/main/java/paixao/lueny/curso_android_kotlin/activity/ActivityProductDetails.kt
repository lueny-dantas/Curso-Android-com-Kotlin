package paixao.lueny.curso_android_kotlin.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import paixao.lueny.curso_android_kotlin.Produto.Product
import paixao.lueny.curso_android_kotlin.databinding.ActivityProductDetailsBinding
import paixao.lueny.curso_android_kotlin.extensions.currencyFormatting
import paixao.lueny.curso_android_kotlin.extensions.tryLoadImage

class ActivityProductDetails: AppCompatActivity() {

    private val binding by lazy {
        ActivityProductDetailsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tryLoadProduct( )
    }

    private fun tryLoadProduct() {
        intent.getParcelableExtra<Product>(PRODUCT_KEY)?.let {
            productLoaded ->  fillFields(productLoaded)
        }?:finish()

    }
    private fun fillFields(productLoaded:Product){
        with(binding){
            activityProductDetailsImage.tryLoadImage(productLoaded.image)
            activityProductDetailsName.text = productLoaded.name
            acttivityProductDetailsDescription.text = productLoaded.description
            activityProductDetailsValue.text = productLoaded.value.currencyFormatting()
        }
    }
}