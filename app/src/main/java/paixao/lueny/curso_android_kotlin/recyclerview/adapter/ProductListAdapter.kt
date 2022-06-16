package paixao.lueny.curso_android_kotlin.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import paixao.lueny.curso_android_kotlin.Produto.Product
import paixao.lueny.curso_android_kotlin.R

class ProductListAdapter(
    private val context: Context,
    private val products: List<Product>
) : RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(product: Product) {
            val name = itemView.findViewById<TextView>(R.id.name)
            name.text = product.name
            val description = itemView.findViewById<TextView>(R.id.description)
            description.text = product.description
            val value = itemView.findViewById<TextView>(R.id.value)
            value.text = product.value.toPlainString()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.product_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int = products.size

}
