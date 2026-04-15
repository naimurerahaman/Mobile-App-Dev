package com.example.universityeventapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EventListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EventAdapter
    private val allEvents = EventData.getSampleEvents().toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_list)
        title = "Browse Events"

        recyclerView = findViewById(R.id.eventRecyclerView)
        adapter = EventAdapter(allEvents)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Category chips
        val chipMap = mapOf(
            R.id.chipAll to "All", R.id.chipTech to "Tech",
            R.id.chipSports to "Sports", R.id.chipCultural to "Cultural",
            R.id.chipAcademic to "Academic", R.id.chipSocial to "Social"
        )
        chipMap.forEach { (id, cat) ->
            findViewById<Button>(id).setOnClickListener {
                val filtered = if (cat == "All") allEvents
                else allEvents.filter { it.category == cat }.toMutableList()
                adapter.updateList(filtered.toMutableList())
            }
        }

        // Search
        findViewById<SearchView>(R.id.eventSearch)
            .setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(q: String?) = false
                override fun onQueryTextChange(text: String?): Boolean {
                    val q = text.orEmpty().lowercase()
                    val filtered = allEvents.filter { it.title.lowercase().contains(q) }.toMutableList()
                    adapter.updateList(filtered)
                    return true
                }
            })
    }
}