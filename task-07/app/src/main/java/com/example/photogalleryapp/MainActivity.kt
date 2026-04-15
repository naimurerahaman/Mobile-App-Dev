package com.example.photogalleryapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var photoGrid: GridView
    private lateinit var selectionToolbar: LinearLayout
    private lateinit var selectionCount: TextView
    private lateinit var deleteButton: Button
    private lateinit var fabAdd: FloatingActionButton

    private lateinit var adapter: PhotoAdapter
    private val allPhotos = mutableListOf<Photo>()
    private var isSelectionMode = false

    // Use built-in drawables for demo (replace with your actual images)
    private val samplePhotos = listOf(
        Photo(1, R.drawable.photo1, "Sunset", "Nature"),
        Photo(2, R.drawable.photo2, "Mountain", "Nature"),
        Photo(3, R.drawable.photo3, "City Night", "City"),
        Photo(4, R.drawable.photo4, "Street Art", "City"),
        Photo(5, R.drawable.photo5, "Dog", "Animals"),
        Photo(6, R.drawable.photo6, "Cat", "Animals"),
        Photo(7, R.drawable.photo7, "Pizza", "Food"),
        Photo(8, R.drawable.photo8, "Sushi", "Food"),
        Photo(9, R.drawable.photo9, "Beach", "Travel"),
        Photo(10, R.drawable.photo10, "Paris", "Travel"),
        Photo(11, R.drawable.photo11, "Forest", "Nature"),
        Photo(12, R.drawable.photo12, "Times Square", "City")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        photoGrid = findViewById(R.id.photoGrid)
        selectionToolbar = findViewById(R.id.selectionToolbar)
        selectionCount = findViewById(R.id.selectionCount)
        deleteButton = findViewById(R.id.deleteButton)
        fabAdd = findViewById(R.id.fabAdd)

        allPhotos.addAll(samplePhotos)
        adapter = PhotoAdapter(this, allPhotos.toMutableList())
        photoGrid.adapter = adapter

        // Category filter buttons
        val categoryButtons = mapOf(
            R.id.btnAll to "All",
            R.id.btnNature to "Nature",
            R.id.btnCity to "City",
            R.id.btnAnimals to "Animals",
            R.id.btnFood to "Food",
            R.id.btnTravel to "Travel"
        )

        categoryButtons.forEach { (btnId, category) ->
            findViewById<Button>(btnId).setOnClickListener {
                val filtered = if (category == "All") allPhotos.toMutableList()
                else allPhotos.filter { it.category == category }.toMutableList()
                adapter.updatePhotos(filtered)
                exitSelectionMode()
            }
        }

        // Normal item click -> open fullscreen
        photoGrid.setOnItemClickListener { _, _, position, _ ->
            if (isSelectionMode) {
                adapter.toggleSelection(position)
                updateSelectionCount()
            } else {
                val photo = adapter.getItem(position) as Photo
                val intent = Intent(this, FullscreenActivity::class.java)
                intent.putExtra("imageResId", photo.resourceId)
                startActivity(intent)
            }
        }

        // Long press -> enter selection mode
        photoGrid.setOnItemLongClickListener { _, _, position, _ ->
            if (!isSelectionMode) {
                isSelectionMode = true
                adapter.setSelectionMode(true)
                selectionToolbar.visibility = View.VISIBLE
            }
            adapter.toggleSelection(position)
            updateSelectionCount()
            true
        }

        // Delete selected
        deleteButton.setOnClickListener {
            val count = adapter.getSelectedCount()
            adapter.deleteSelected()
            exitSelectionMode()
            Toast.makeText(this, "$count photos deleted", Toast.LENGTH_SHORT).show()
        }

        // FAB: simulate adding a photo
        fabAdd.setOnClickListener {
            val newPhoto = Photo(
                id = allPhotos.size + 1,
                resourceId = R.mipmap.ic_launcher,
                title = "New Photo ${allPhotos.size + 1}",
                category = "Nature"
            )
            allPhotos.add(newPhoto)
            adapter.updatePhotos(allPhotos.toMutableList())
            Toast.makeText(this, "Photo added!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateSelectionCount() {
        selectionCount.text = "${adapter.getSelectedCount()} selected"
    }

    private fun exitSelectionMode() {
        isSelectionMode = false
        adapter.setSelectionMode(false)
        selectionToolbar.visibility = View.GONE
    }
}