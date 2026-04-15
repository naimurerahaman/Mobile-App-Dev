package com.example.ecommerceapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var cartBadge: TextView
    private lateinit var toggleViewBtn: ImageButton
    private lateinit var emptyText: TextView
    private lateinit var adapter: ProductAdapter

    private val allProducts = mutableListOf(
        Product(1, "Smartphone X12", 299.99, 4.5f, "Electronics", R.mipmap.ic_launcher),
        Product(2, "Laptop Pro", 799.99, 4.8f, "Electronics", R.mipmap.ic_launcher_round),
        Product(3, "Blue T-Shirt", 19.99, 3.9f, "Clothing", R.drawable.ic_launcher_background),
        Product(4, "Jeans Classic", 49.99, 4.2f, "Clothing", R.mipmap.ic_launcher),
        Product(5, "Kotlin Book", 29.99, 4.7f, "Books", R.mipmap.ic_launcher_round),
        Product(6, "Android Book", 34.99, 4.6f, "Books", R.drawable.ic_launcher_background),
        Product(7, "Chocolate Cake", 12.99, 4.0f, "Food", R.mipmap.ic_launcher),
        Product(8, "Organic Coffee", 15.99, 4.3f, "Food", R.mipmap.ic_launcher_round),
        Product(9, "Lego Set", 59.99, 4.9f, "Toys", R.drawable.ic_launcher_background),
        Product(10, "RC Car", 39.99, 4.1f, "Toys", R.mipmap.ic_launcher)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.searchView)
        cartBadge = findViewById(R.id.cartBadge)
        toggleViewBtn = findViewById(R.id.toggleViewBtn)
        emptyText = findViewById(R.id.emptyText)

        adapter = ProductAdapter(allProducts.toMutableList(), true) { cartCount ->
            cartBadge.text = "Cart: $cartCount"
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Toggle list/grid view
        toggleViewBtn.setOnClickListener {
            adapter.toggleMode()
            recyclerView.layoutManager = if (adapter.isListMode())
                LinearLayoutManager(this)
            else
                GridLayoutManager(this, 2)
        }

        // Category filter
        val catMap = mapOf(
            R.id.catAll to "All",
            R.id.catElectronics to "Electronics",
            R.id.catClothing to "Clothing",
            R.id.catBooks to "Books",
            R.id.catFood to "Food",
            R.id.catToys to "Toys"
        )
        catMap.forEach { (btnId, category) ->
            findViewById<Button>(btnId).setOnClickListener {
                val filtered = if (category == "All") allProducts.toMutableList()
                else allProducts.filter { it.category == category }.toMutableList()
                adapter.updateProducts(filtered)
                checkEmpty(filtered)
            }
        }

        // Search
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false
            override fun onQueryTextChange(text: String?): Boolean {
                val q = text.orEmpty().lowercase()
                val filtered = allProducts.filter { it.name.lowercase().contains(q) }.toMutableList()
                adapter.updateProducts(filtered)
                checkEmpty(filtered)
                return true
            }
        })

        // Swipe to delete with undo
        val touchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(rv: RecyclerView, vh: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder): Boolean {
                // Drag to reorder
                val from = vh.adapterPosition
                val to = target.adapterPosition
                val current = adapter.removeItem(from)
                adapter.insertItem(to, current)
                return true
            }

            override fun onSwiped(vh: RecyclerView.ViewHolder, direction: Int) {
                val position = vh.adapterPosition
                val removed = adapter.removeItem(position)
                Snackbar.make(recyclerView, "${removed.name} removed", Snackbar.LENGTH_LONG)
                    .setAction("UNDO") {
                        adapter.insertItem(position, removed)
                    }.show()
            }
        })
        touchHelper.attachToRecyclerView(recyclerView)
    }

    private fun checkEmpty(list: List<Product>) {
        emptyText.visibility = if (list.isEmpty()) android.view.View.VISIBLE else android.view.View.GONE
        recyclerView.visibility = if (list.isEmpty()) android.view.View.GONE else android.view.View.VISIBLE
    }
}