package paixao.lueny.curso_android_kotlin.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import paixao.lueny.curso_android_kotlin.model.Product
import paixao.lueny.curso_android_kotlin.R
import paixao.lueny.curso_android_kotlin.database.AppDatabase
import paixao.lueny.curso_android_kotlin.databinding.ActivityProductsListBinding
import paixao.lueny.curso_android_kotlin.recyclerview.adapter.ProductListAdapter


class ActivityProductsList : AppCompatActivity() {

    private val adapter = ProductListAdapter(context = this, products = emptyList())
    private val binding by lazy { ActivityProductsListBinding.inflate(layoutInflater) }
    private val productDao by lazy { AppDatabase.instance(this).productDao() }
    private val userDao by lazy { AppDatabase.instance(this).userDao() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configureRecyclerView()
        configureFab()
        lifecycleScope.launch {
            productDao.searchAll().collect { products ->
                adapter.update()
            }
            intent.getStringExtra("KEY_USER_ID")?.let { userId ->
                lifecycleScope.launch {
                    val user = userDao.searchById(userId)
                    Log.i("listaProdutos", "onCreate: $user")
                }
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_list_products, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val sortedProducts: Flow<List<Product>>? = when (item.itemId) {
            R.id.menu_list_product_sort_name_asc ->
                productDao.searchAllSortbyNameAsc()
            R.id.menu_list_product_sort_name_desc ->
                productDao.searchAllSortByNameDesc()
            R.id.menu_list_product_sort_description_asc ->
                productDao.searchAllSortByDescriptionAsc()
            R.id.menu_list_product_sort_description_desc ->
                productDao.searchAllSortByDescriptionDesc()
            R.id.menu_list_product_sort_value_asc ->
                productDao.searchAllSortByValueAsc()
            R.id.menu_list_product_sort_value_desc ->
                productDao.searchAllSortByValueDesc()
            R.id.menu_list_product_without_sort ->
                productDao.searchAll()
            else -> null

        }

        lifecycleScope.launch {
            sortedProducts?.collect {
                adapter.update()
            }
        }

        return super.onOptionsItemSelected(item)
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
                putExtra(ID_PRODUCT_KEY, it.id)
            }
            startActivity(intent)
        }
    }
}