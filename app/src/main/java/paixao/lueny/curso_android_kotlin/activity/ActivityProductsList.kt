package paixao.lueny.curso_android_kotlin.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import paixao.lueny.curso_android_kotlin.Produto.Product
import paixao.lueny.curso_android_kotlin.R
import paixao.lueny.curso_android_kotlin.database.AppDatabase
import paixao.lueny.curso_android_kotlin.databinding.ActivityProductsListBinding
import paixao.lueny.curso_android_kotlin.recyclerview.adapter.ProductListAdapter

//private const val TAG = "ActivityProductList"

class ActivityProductsList : AppCompatActivity() {

    private val adapter = ProductListAdapter(context = this, products = emptyList())
    private val binding by lazy { ActivityProductsListBinding.inflate(layoutInflater) }
    private val productDao by lazy { AppDatabase.instance(this).productDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configureRecyclerView()
        configureFab()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_list_products, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val sortedProducts: List<Product>? = when (item.itemId) {
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
        sortedProducts?.let {
            adapter.update(it)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        val scope = CoroutineScope(IO)
        scope.launch {
            val products = withContext(IO) {
                productDao.searchAll()
            }
        adapter.update(products)
        }
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
//        adapter.whenClickEdit = {
//            Log.i(TAG, "configureRecyclerView: Edit $it")
//        }
//        adapter.whenClickRemove = {
//            Log.i(TAG, "configureRecyclerView: Remove $it")
//        }
    }
}