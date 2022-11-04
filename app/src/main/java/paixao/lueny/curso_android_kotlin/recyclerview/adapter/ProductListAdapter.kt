package paixao.lueny.curso_android_kotlin.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import paixao.lueny.curso_android_kotlin.Produto.Product
import paixao.lueny.curso_android_kotlin.R
import paixao.lueny.curso_android_kotlin.databinding.ProductItemBinding
import paixao.lueny.curso_android_kotlin.extensions.tryLoadImage
import java.text.NumberFormat
import java.util.*

class ProductListAdapter(
    private val context: Context,
    products: List<Product>
) : RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    private val products = products.toMutableList()

    class ViewHolder(private val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            val name = binding.activityProductsListName
            name.text = product.name
            val description = binding.activityProductsListDescription
            description.text = product.description
            val value = binding.activityProductsListValue
            val currencyValue: String = currencyFormatter(product)
            value.text = currencyValue

            val visibility = if (product.image != null) {
                View.VISIBLE
            } else {
                View.GONE
            }
            binding.activityProductsListImageView.visibility = visibility

            binding.activityProductsListImageView.tryLoadImage(product.image)


        }

        private fun currencyFormatter(product: Product): String {
            val currencyFormatter: NumberFormat = NumberFormat
                .getCurrencyInstance(Locale("pt", "br"))
            return currencyFormatter.format(product.value)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = ProductItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int = products.size

    fun update(products: List<Product>) {
        this.products.clear()
        this.products.addAll(products)
        notifyDataSetChanged()

    }

}
