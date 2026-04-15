package com.example.photogalleryapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class PhotoAdapter(
    private val context: Context,
    private var photos: MutableList<Photo>,
    private var isSelectionMode: Boolean = false
) : BaseAdapter() {

    override fun getCount() = photos.size
    override fun getItem(position: Int) = photos[position]
    override fun getItemId(position: Int) = photos[position].id.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_photo, parent, false)

        val photo = photos[position]
        val imageView = view.findViewById<ImageView>(R.id.photoImage)
        val titleView = view.findViewById<TextView>(R.id.photoTitle)
        val checkBox = view.findViewById<CheckBox>(R.id.photoCheckBox)

        imageView.setImageResource(photo.resourceId)
        titleView.text = photo.title
        checkBox.visibility = if (isSelectionMode) View.VISIBLE else View.GONE
        checkBox.isChecked = photo.isSelected

        return view
    }

    fun setSelectionMode(enabled: Boolean) {
        isSelectionMode = enabled
        if (!enabled) photos.forEach { it.isSelected = false }
        notifyDataSetChanged()
    }

    fun toggleSelection(position: Int) {
        photos[position].isSelected = !photos[position].isSelected
        notifyDataSetChanged()
    }

    fun getSelectedCount() = photos.count { it.isSelected }

    fun deleteSelected() {
        photos.removeAll { it.isSelected }
        notifyDataSetChanged()
    }

    fun filterByCategory(category: String) {
        // This is handled in MainActivity using the full list
    }

    fun updatePhotos(newPhotos: MutableList<Photo>) {
        photos = newPhotos
        notifyDataSetChanged()
    }
}