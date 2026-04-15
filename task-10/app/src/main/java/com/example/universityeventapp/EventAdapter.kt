package com.example.universityeventapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class EventAdapter(private var events: MutableList<Event>)
    : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    inner class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.eventImage)
        val title: TextView = view.findViewById(R.id.eventTitle)
        val date: TextView = view.findViewById(R.id.eventDate)
        val category: TextView = view.findViewById(R.id.eventCategory)
        val venue: TextView = view.findViewById(R.id.eventVenue)
        val seats: TextView = view.findViewById(R.id.eventSeats)
        val price: TextView = view.findViewById(R.id.eventPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        EventViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_event, parent, false))

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.image.setImageResource(event.imageRes)
        holder.title.text = event.title
        holder.date.text = "${event.date} | ${event.time}"
        holder.category.text = event.category
        holder.venue.text = "📍 ${event.venue}"
        holder.seats.text = "Seats: ${event.availableSeats}"
        holder.price.text = if (event.price == 0.0) "Free" else "৳${"%.0f".format(event.price)}"

        holder.itemView.setOnClickListener {
            val ctx = holder.itemView.context
            val intent = Intent(ctx, EventDetailActivity::class.java)
            intent.putExtra("event", event)
            ctx.startActivity(intent)
        }
    }

    override fun getItemCount() = events.size

    fun updateList(newEvents: MutableList<Event>) {
        events = newEvents
        notifyDataSetChanged()
    }
}