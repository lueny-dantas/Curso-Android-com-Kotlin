package paixao.lueny.curso_android_kotlin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import paixao.lueny.curso_android_kotlin.Produto.Product
import paixao.lueny.curso_android_kotlin.R
import paixao.lueny.curso_android_kotlin.recyclerview.adapter.ProductListAdapter
import java.math.BigDecimal

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.adapter = ProductListAdapter(context = this, products = listOf(
                Product(
                    name = "Açúcar", description = "Cristal", value = BigDecimal("4.20")
                ),
                Product(
                    name = "Arroz", description = "Dalon", value = BigDecimal("3.20")
                ),
                Product(
                    name = "Banana", description = "Nanica", value = BigDecimal("4.50")
                ),
                Product(
                    name = "Cuscuz", description = "Flocão", value = BigDecimal("2.70")
                ),
        ))
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}