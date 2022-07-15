package paixao.lueny.curso_android_kotlin.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import paixao.lueny.curso_android_kotlin.R
import paixao.lueny.curso_android_kotlin.dao.DaoProducts
import paixao.lueny.curso_android_kotlin.recyclerview.adapter.ProductListAdapter

class ActivityProductsList : AppCompatActivity(R.layout.activity_products_list) {

    private val dao = DaoProducts()
    private val adapter =  ProductListAdapter(context = this, products = dao.searchAll())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureRecyclerView()
        configureFab()
    }

    override fun onResume() {
        super.onResume()
        adapter.update(dao.searchAll())
    }

    private fun configureFab() {
        val fab = findViewById<FloatingActionButton>(R.id.activity_products_list_fab)
        fab.setOnClickListener {
            accessForm()
        }
    }

    private fun accessForm() {
        val intent = Intent(this, ActivityProductForm::class.java)
        startActivity(intent)
    }

    private fun configureRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.activity_products_list_recyclerview)
        recyclerView.adapter = adapter
    }
}