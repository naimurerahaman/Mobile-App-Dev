package com.example.universityeventapp

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class SeatBookingActivity : AppCompatActivity() {

    private lateinit var seatGrid: GridView
    private lateinit var selectedCountText: TextView
    private lateinit var totalPriceText: TextView
    private lateinit var confirmBtn: Button

    private val totalSeats = 48 // 6 columns x 8 rows
    private val seatStates = IntArray(totalSeats) // 0=available, 1=booked, 2=selected
    private var event: Event? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seat_booking)

        event = intent.getSerializableExtra("event") as? Event
        title = "Book Seats - ${event?.title}"

        seatGrid = findViewById(R.id.seatGrid)
        selectedCountText = findViewById(R.id.selectedCountText)
        totalPriceText = findViewById(R.id.totalPriceText)
        confirmBtn = findViewById(R.id.confirmBtn)

        // Randomly mark ~30% as booked
        repeat(totalSeats) { i ->
            seatStates[i] = if (Math.random() < 0.3) 1 else 0
        }

        val adapter = SeatAdapter()
        seatGrid.adapter = adapter

        seatGrid.setOnItemClickListener { _, _, position, _ ->
            if (seatStates[position] == 1) {
                Toast.makeText(this, "Seat already booked!", Toast.LENGTH_SHORT).show()
            } else {
                seatStates[position] = if (seatStates[position] == 2) 0 else 2
                adapter.notifyDataSetChanged()
                updateSummary()
            }
        }

        confirmBtn.setOnClickListener {
            val count = seatStates.count { it == 2 }
            if (count == 0) {
                Toast.makeText(this, "Please select at least one seat", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Booking confirmed for $count seats!", Toast.LENGTH_LONG).show()
                finish()
            }
        }

        // Confirm dialog on back if seats selected
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun updateSummary() {
        val count = seatStates.count { it == 2 }
        val total = count * (event?.price ?: 0.0)
        selectedCountText.text = "$count seats selected"
        totalPriceText.text = "Total: ৳${"%.0f".format(total)}"
    }

    inner class SeatAdapter : BaseAdapter() {
        override fun getCount() = totalSeats
        override fun getItem(pos: Int) = pos
        override fun getItemId(pos: Int) = pos.toLong()

        override fun getView(pos: Int, convertView: View?, parent: ViewGroup): View {
            val btn = convertView as? Button ?: Button(this@SeatBookingActivity)
            btn.text = "${pos + 1}"
            btn.textSize = 10f
            btn.setPadding(2, 2, 2, 2)
            val color = when (seatStates[pos]) {
                1 -> Color.parseColor("#F44336") // booked = red
                2 -> Color.parseColor("#2196F3") // selected = blue
                else -> Color.parseColor("#4CAF50") // available = green
            }
            btn.setBackgroundColor(color)
            btn.setTextColor(Color.WHITE)
            return btn
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val selectedCount = seatStates.count { it == 2 }
        if (selectedCount > 0) {
            AlertDialog.Builder(this)
                .setTitle("Leave?")
                .setMessage("You have $selectedCount seats selected. Leave without booking?")
                .setPositiveButton("Leave") { _, _ -> super.onBackPressed() }
                .setNegativeButton("Stay", null)
                .show()
        } else {
            super.onBackPressed()
        }
    }
}