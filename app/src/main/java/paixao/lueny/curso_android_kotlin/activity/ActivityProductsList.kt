package paixao.lueny.curso_android_kotlin.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import paixao.lueny.curso_android_kotlin.R
import paixao.lueny.curso_android_kotlin.database.AppDatabase
import paixao.lueny.curso_android_kotlin.databinding.ActivityProductsListBinding
import paixao.lueny.curso_android_kotlin.extensions.goTo
import paixao.lueny.curso_android_kotlin.model.Product
import paixao.lueny.curso_android_kotlin.model.User
import paixao.lueny.curso_android_kotlin.recyclerview.adapter.ProductListAdapter


class ActivityProductsList : ActivityBase() {

    private val adapter = ProductListAdapter(context = this, products = emptyList())
    private val binding by lazy { ActivityProductsListBinding.inflate(layoutInflater) }
    private val productDao by lazy { AppDatabase.instance(this).productDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configureRecyclerView()
        configureFab()

        lifecycleScope.launch {
                user
                    .filterNotNull()
                    .collect { user: User ->
//                        searchProductsUser()
                        searchProductsUserId(user.id)
                    }

        }
    }

    private suspend fun searchProductsUser() {
        productDao.searchAll().collect { products ->
            adapter.update(products)
        }
    }

    private suspend fun searchProductsUserId(userId:String) {
        productDao.searchAllProductByUserId(userId).collect { products ->
            adapter.update(products)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_list_products, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        lifecycleScope.launch {
            user
                .filterNotNull()
                .collect{ user ->
                    val sortedProducts: Flow<List<Product>>? = when (item.itemId) {
                        R.id.menu_list_product_sort_name_asc ->
                            productDao.searchAllSortByNameAsc(userId = user.id)
                        R.id.menu_list_product_sort_name_desc ->
                            productDao.searchAllSortByNameDesc(userId = user.id)
                        R.id.menu_list_product_sort_description_asc ->
                            productDao.searchAllSortByDescriptionAsc(userId = user.id)
                        R.id.menu_list_product_sort_description_desc ->
                            productDao.searchAllSortByDescriptionDesc(userId = user.id)
                        R.id.menu_list_product_sort_value_asc ->
                            productDao.searchAllSortByValueAsc(userId = user.id)
                        R.id.menu_list_product_sort_value_desc ->
                            productDao.searchAllSortByValueDesc(userId = user.id)
                        R.id.menu_list_product_without_sort ->
                            productDao.searchAll()
                        else -> null
                    }
                    sortedProducts?.collect { products ->
                        adapter.update(products)
                    }
                }
        }

        when (item.itemId) {
            R.id.menu_list_product_user_profile -> {
                goTo(ActivityUserProfile::class.java)
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