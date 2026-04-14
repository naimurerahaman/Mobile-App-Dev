package com.example.newsreaderapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView

class MainActivity : AppCompatActivity() {

    private lateinit var mainScrollView: NestedScrollView
    private lateinit var bookmarkButton: ImageButton
    private lateinit var shareButton: ImageButton
    private lateinit var backToTopButton: Button
    private lateinit var articleTitle: TextView

    private var isBookmarked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainScrollView = findViewById(R.id.mainScrollView)
        bookmarkButton = findViewById(R.id.bookmarkButton)
        shareButton = findViewById(R.id.shareButton)
        backToTopButton = findViewById(R.id.backToTopButton)
        articleTitle = findViewById(R.id.articleTitle)

        val sectionIntro = findViewById<TextView>(R.id.sectionIntro)
        val sectionKeyPoints = findViewById<TextView>(R.id.sectionKeyPoints)
        val sectionAnalysis = findViewById<TextView>(R.id.sectionAnalysis)
        val sectionConclusion = findViewById<TextView>(R.id.sectionConclusion)

        // Quick nav buttons - scroll to section
        fun scrollToView(view: TextView) {
            mainScrollView.post {
                mainScrollView.smoothScrollTo(0, view.top - 16)
            }
        }

        findViewById<Button>(R.id.btnIntro).setOnClickListener { scrollToView(sectionIntro) }
        findViewById<Button>(R.id.btnKeyPoints).setOnClickListener { scrollToView(sectionKeyPoints) }
        findViewById<Button>(R.id.btnAnalysis).setOnClickListener { scrollToView(sectionAnalysis) }
        findViewById<Button>(R.id.btnConclusion).setOnClickListener { scrollToView(sectionConclusion) }

        // Back to top
        backToTopButton.setOnClickListener {
            mainScrollView.smoothScrollTo(0, 0)
        }

        // Bookmark toggle
        bookmarkButton.setOnClickListener {
            isBookmarked = !isBookmarked
            if (isBookmarked) {
                bookmarkButton.setImageResource(android.R.drawable.btn_star_big_on)
                Toast.makeText(this, "Article Bookmarked", Toast.LENGTH_SHORT).show()
            } else {
                bookmarkButton.setImageResource(android.R.drawable.btn_star_big_off)
                Toast.makeText(this, "Bookmark Removed", Toast.LENGTH_SHORT).show()
            }
        }

        // Share article
        shareButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, articleTitle.text.toString())
            startActivity(Intent.createChooser(intent, "Share Article"))
        }
    }
}