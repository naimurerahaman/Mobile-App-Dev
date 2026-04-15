package com.example.ecommerceapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter(
    private var products: MutableList<Product>,
    private var isListMode: Boolean = true,
    private val onCartChanged: (Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val LIST_VIEW = 0
        const val GRID_VIEW = 1
    }

    override fun getItemViewType(position: Int) = if (isListMode) LIST_VIEW else GRID_VIEW

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == LIST_VIEW) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_product_list, parent, false)
            ListViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_product_grid, parent, false)
            GridViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val product = products[position]
        if (holder is ListViewHolder) {
            holder.bind(product)
        } else if (holder is GridViewHolder) {
            holder.bind(product)
        }
    }

    override fun getItemCount() = products.size

    inner class ListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val image: ImageView = view.findViewById(R.id.productImage)
        private val name: TextView = view.findViewById(R.id.productName)
        private val category: TextView = view.findViewById(R.id.productCategory)
        private val rating: RatingBar = view.findViewById(R.id.ratingBar)
        private val price: TextView = view.findViewById(R.id.productPrice)
        private val cartBtn: Button = view.findViewById(R.id.addToCartBtn)

        fun bind(product: Product) {
            image.setImageResource(product.imageRes)
            name.text = product.name
            category.text = product.category
            rating.rating = product.rating
            price.text = "$${"%.2f".format(product.price)}"
            cartBtn.text = if (product.inCart) "Remove" else "Add"
            cartBtn.setOnClickListener {
                product.inCart = !product.inCart
                cartBtn.text = if (product.inCart) "Remove" else "Add"
                onCartChanged(products.count { it.inCart })
            }
        }
    }

    inner class GridViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val image: ImageView = view.findViewById(R.id.productImage)
        private val name: TextView = view.findViewById(R.id.productName)
        private val price: TextView = view.findViewById(R.id.productPrice)
        private val cartBtn: ImageButton = view.findViewById(R.id.addToCartBtn)

        fun bind(product: Product) {
            image.setImageResource(product.imageRes)
            name.text = product.name
            price.text = "$${"%.2f".format(product.price)}"
            cartBtn.setOnClickListener {
                product.inCart = !product.inCart
                onCartChanged(products.count { it.inCart })
            }
        }
    }

    fun toggleMode() {
        isListMode = !isListMode
        notifyDataSetChanged()
    }

    fun updateProducts(newList: MutableList<Product>) {
        products = newList
        notifyDataSetChanged()
    }

    fun removeItem(position: Int): Product {
        val removed = products.removeAt(position)
        notifyItemRemoved(position)
        return removed
    }

    fun insertItem(position: Int, product: Product) {
        products.add(position, product)
        notifyItemInserted(position)
    }

    fun isListMode() = isListMode
}