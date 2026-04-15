package com.example.universityeventapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.browseEvents).setOnClickListener {
            startActivity(Intent(this, EventListActivity::class.java))
        }
        findViewById<Button>(R.id.registerNowBtn).setOnClickListener {
            startActivity(Intent(this, EventListActivity::class.java))
        }
        findViewById<Button>(R.id.myBookings).setOnClickListener {
            Toast.makeText(this, "No bookings yet!", Toast.LENGTH_SHORT).show()
        }
        findViewById<Button>(R.id.notifications).setOnClickListener {
            Toast.makeText(this, "No new notifications", Toast.LENGTH_SHORT).show()
        }
        findViewById<Button>(R.id.profile).setOnClickListener {
            Toast.makeText(this, "Profile coming soon", Toast.LENGTH_SHORT).show()
        }
    }
}