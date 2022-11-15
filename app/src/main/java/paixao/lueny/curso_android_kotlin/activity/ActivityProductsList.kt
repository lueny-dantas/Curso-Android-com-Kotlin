package paixao.lueny.curso_android_kotlin.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import paixao.lueny.curso_android_kotlin.database.AppDatabase
import paixao.lueny.curso_android_kotlin.databinding.ActivityProductsListBinding
import paixao.lueny.curso_android_kotlin.recyclerview.adapter.ProductListAdapter

class ActivityProductsList : AppCompatActivity() {

    private val adapter = ProductListAdapter(context = this, products = emptyList())
    private val binding by lazy {
        ActivityProductsListBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configureRecyclerView()
        configureFab()
    }

    override fun onResume() {
        super.onResume()
        val db = AppDatabase.instance(this)
        val productDao = db.productDao()
        adapter.update(productDao.searchAll())
    }

    private fun configureFab() {
        val fab = binding.activityProductsListFab
        fab.setOnClickListener {
            accessForm()

        }
    }

    private fun accessForm() {
        val intent = Intent(this, ActivityProductForm::class.java)
        startActivity(intent)
    }

    private fun configureRecyclerView() {
        val recyclerView = binding.activityProductsListRecyclerview
        recyclerView.adapter = adapter
        adapter.whenClickItem = {
            val intent = Intent(
                this,
                ActivityProductDetails::class.java
            ).apply {
                putExtra(PRODUCT_KEY, it)
            }
            startActivity(intent)
        }
    }
}