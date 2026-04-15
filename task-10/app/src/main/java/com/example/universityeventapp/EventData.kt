package com.example.universityeventapp

object EventData {
    fun getSampleEvents(): List<Event> = listOf(
        Event(1, "Tech Innovation Summit", "2026-05-10", "10:00 AM", "Main Auditorium",
            "Tech", "Explore the latest in AI, robotics, and software development.", 200.0, 200, 120, android.R.drawable.ic_menu_gallery),
        Event(2, "Annual Football Tournament", "2026-05-15", "3:00 PM", "Sports Ground",
            "Sports", "Inter-department football championship. Come support your team!", 50.0, 500, 300, android.R.drawable.ic_menu_gallery),
        Event(3, "Cultural Fest 2026", "2026-05-20", "5:00 PM", "Open Ground",
            "Cultural", "Music, dance, food stalls and cultural performances.", 100.0, 1000, 650, android.R.drawable.ic_menu_gallery),
        Event(4, "Academic Research Symposium", "2026-05-25", "9:00 AM", "Conference Hall",
            "Academic", "Undergraduate and graduate students present their research.", 0.0, 150, 80, android.R.drawable.ic_menu_gallery),
        Event(5, "Networking Night", "2026-06-01", "7:00 PM", "Student Lounge",
            "Social", "Connect with alumni and industry professionals.", 150.0, 100, 45, android.R.drawable.ic_menu_gallery),
        Event(6, "Hackathon 2026", "2026-06-05", "8:00 AM", "Computer Lab",
            "Tech", "24-hour coding challenge. Form teams and build something amazing!", 250.0, 80, 30, android.R.drawable.ic_menu_gallery),
        Event(7, "Inter-University Basketball", "2026-06-10", "2:00 PM", "Indoor Stadium",
            "Sports", "Watch our team compete against 8 universities.", 75.0, 300, 200, android.R.drawable.ic_menu_gallery),
        Event(8, "Art Exhibition", "2026-06-15", "11:00 AM", "Arts Gallery",
            "Cultural", "Annual art exhibition showcasing student artwork.", 0.0, 200, 180, android.R.drawable.ic_menu_gallery)
    )
}