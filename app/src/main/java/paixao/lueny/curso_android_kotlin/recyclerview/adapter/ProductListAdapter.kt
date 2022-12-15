package paixao.lueny.curso_android_kotlin.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.Flow
import paixao.lueny.curso_android_kotlin.model.Product
import paixao.lueny.curso_android_kotlin.databinding.ProductItemBinding
import paixao.lueny.curso_android_kotlin.extensions.currencyFormatting
import paixao.lueny.curso_android_kotlin.extensions.tryLoadImage


class ProductListAdapter(
    private val context: Context,
    products: List<Product> = emptyList(),
    var whenClickItem:(product: Product) -> Unit = {},
//    var whenClickEdit:(product:Product) -> Unit = {},
//    var whenClickRemove:(product:Product) -> Unit = {}
) : RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    private val products = products.toMutableList()

    inner class ViewHolder(private val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root)/*PopupMenu.OnMenuItemClickListener*/ {
        private lateinit var product: Product

        init {
            itemView.setOnClickListener {
                if (::product.isInitialized){
                    whenClickItem (product)
                }
            }
//            itemView.setOnClickListener {
//                PopupMenu(context,itemView).apply {
//                    menuInflater.inflate(R.menu.menu_product_details,menu)
//                    setOnMenuItemClickListener(this@ViewHolder)
//                }.show()
//                true
//            }
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

//        override fun onMenuItemClick(item: MenuItem?): Boolean {
//            item?.let {
//                when (item.itemId) {
//                    R.id.menu_product_details_remove -> {
//                        whenClickRemove(product)
//                    }
//                    R.id.menu_product_details_edit -> {
//                        whenClickEdit(product)
//                    }
//                }
//            }
//            return true
//        }
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

    fun update() {
        this.products.clear()
        this.products.addAll(products)
        notifyDataSetChanged()

    }

}
