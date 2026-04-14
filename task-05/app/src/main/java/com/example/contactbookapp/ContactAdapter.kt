package com.example.contactbookapp

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class ContactAdapter(context: Context, private val contacts: MutableList<Contact>)
    : ArrayAdapter<Contact>(context, R.layout.item_contact, contacts) {

    // Array of colors for avatars
    private val colors = arrayOf(
        "#E53935", "#8E24AA", "#1E88E5", "#00897B",
        "#F4511E", "#3949AB", "#039BE5", "#43A047"
    )

    // ViewHolder for better performance
    private class ViewHolder(view: View) {
        val avatarText: TextView = view.findViewById(R.id.avatarText)
        val contactName: TextView = view.findViewById(R.id.contactName)
        val contactPhone: TextView = view.findViewById(R.id.contactPhone)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val holder: ViewHolder
        val view: View

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }

        val contact = contacts[position]

        // Set data
        holder.contactName.text = contact.name
        holder.contactPhone.text = contact.phone
        holder.avatarText.text = contact.initial

        // Set avatar color based on first letter
        val colorIndex = (contact.name.first().uppercaseChar() - 'A') % colors.size
        holder.avatarText.setBackgroundColor(Color.parseColor(colors[colorIndex]))

        return view
    }
}