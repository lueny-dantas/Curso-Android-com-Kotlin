package paixao.lueny.curso_android_kotlin.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import paixao.lueny.curso_android_kotlin.Produto.Product
import paixao.lueny.curso_android_kotlin.databinding.ProductItemBinding
import paixao.lueny.curso_android_kotlin.extensions.currencyFormatting
import paixao.lueny.curso_android_kotlin.extensions.tryLoadImage


class ProductListAdapter(
    private val context: Context,
    products: List<Product> = emptyList(),
    var whenClickItem:(product:Product) -> Unit = {}
) : RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    private val products = products.toMutableList()

    inner class ViewHolder(private val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var product: Product

        init {
            itemView.setOnClickListener {
                if (::product.isInitialized){
                    whenClickItem (product)
                }
            }
        }

        fun bind(product: Product) {
            this.product = product
            val name = binding.activityProductsListName
            name.text = product.name
            val description = binding.activityProductsListDescription
            description.text = product.description
            val value = binding.activityProductsListValue
            val currencyValue: String = product.value.currencyFormatting()
            value.text = currencyValue

            val visibility = if (product.image != null) {
                View.VISIBLE
            } else {
                View.GONE
            }
            binding.activityProductsListImageView.visibility = visibility

            binding.activityProductsListImageView.tryLoadImage(product.image)


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
