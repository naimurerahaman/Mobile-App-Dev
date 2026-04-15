package com.example.universityeventapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class EventDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        val event = intent.getSerializableExtra("event") as? Event ?: return
        title = event.title

        findViewById<ImageView>(R.id.detailEventImage).setImageResource(event.imageRes)
        findViewById<TextView>(R.id.detailTitle).text = event.title
        findViewById<TextView>(R.id.detailDate).text = "${event.date} | ${event.time}"
        findViewById<TextView>(R.id.detailCategory).text = event.category
        findViewById<TextView>(R.id.detailVenue).text = "📍 ${event.venue}"
        findViewById<TextView>(R.id.detailDescription).text = event.description
        findViewById<TextView>(R.id.detailSeats).text = "Available: ${event.availableSeats}"
        val priceText = if (event.price == 0.0) "Free" else "৳${"%.0f".format(event.price)}"
        findViewById<TextView>(R.id.detailPrice).text = priceText

        // Register -> go to seat booking
        findViewById<Button>(R.id.registerBtn).setOnClickListener {
            val intent = Intent(this, SeatBookingActivity::class.java)
            intent.putExtra("event", event)
            startActivity(intent)
        }
    }
}