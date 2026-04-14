package com.example.gradereportapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var gradeTable: TableLayout
    private lateinit var gpaText: TextView
    private lateinit var summaryText: TextView
    private lateinit var subjectNameInput: EditText
    private lateinit var obtainedMarksInput: EditText
    private lateinit var totalMarksInput: EditText
    private lateinit var addButton: Button
    private lateinit var shareButton: Button

    data class SubjectEntry(val name: String, val obtained: Int, val total: Int, val grade: String)
    private val subjects = mutableListOf<SubjectEntry>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gradeTable = findViewById(R.id.gradeTable)
        gpaText = findViewById(R.id.gpaText)
        summaryText = findViewById(R.id.summaryText)
        subjectNameInput = findViewById(R.id.subjectNameInput)
        obtainedMarksInput = findViewById(R.id.obtainedMarksInput)
        totalMarksInput = findViewById(R.id.totalMarksInput)
        addButton = findViewById(R.id.addButton)
        shareButton = findViewById(R.id.shareButton)

        // Pre-load 6 default subjects
        listOf(
            Triple("Mathematics", 88, 100),
            Triple("Physics", 72, 100),
            Triple("Chemistry", 55, 100),
            Triple("English", 91, 100),
            Triple("Programming", 80, 100),
            Triple("Data Structures", 38, 100)
        ).forEach { (name, obtained, total) ->
            val grade = calculateGrade(obtained, total)
            subjects.add(SubjectEntry(name, obtained, total, grade))
            addRowToTable(SubjectEntry(name, obtained, total, grade))
        }
        updateGPA()

        addButton.setOnClickListener {
            val name = subjectNameInput.text.toString().trim()
            val obtainedStr = obtainedMarksInput.text.toString().trim()
            val totalStr = totalMarksInput.text.toString().trim()

            if (name.isEmpty() || obtainedStr.isEmpty() || totalStr.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val obtained = obtainedStr.toInt()
            val total = totalStr.toInt()

            if (obtained > total) {
                Toast.makeText(this, "Obtained marks cannot exceed total!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val grade = calculateGrade(obtained, total)
            val entry = SubjectEntry(name, obtained, total, grade)
            subjects.add(entry)
            addRowToTable(entry)
            updateGPA()

            subjectNameInput.text.clear()
            obtainedMarksInput.text.clear()
            totalMarksInput.text.clear()
        }

        shareButton.setOnClickListener {
            val gpa = calculateGPAValue()
            val passed = subjects.count { it.grade != "F" }
            val text = "My Grade Report\nSubjects: ${subjects.size}\nPassed: $passed\nGPA: ${"%.2f".format(gpa)}"
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, text)
            startActivity(Intent.createChooser(intent, "Share Report"))
        }
    }

    private fun calculateGrade(obtained: Int, total: Int): String {
        val pct = (obtained.toFloat() / total) * 100
        return when {
            pct >= 90 -> "A+"
            pct >= 80 -> "A"
            pct >= 70 -> "B+"
            pct >= 60 -> "B"
            pct >= 50 -> "C"
            pct >= 40 -> "D"
            else -> "F"
        }
    }

    private fun gradeToGPA(grade: String) = when (grade) {
        "A+" -> 4.0; "A" -> 3.7; "B+" -> 3.3; "B" -> 3.0
        "C" -> 2.0; "D" -> 1.0; else -> 0.0
    }

    private fun addRowToTable(entry: SubjectEntry) {
        val row = TableRow(this)
        val isPassed = entry.grade != "F"
        val rowBg = if (!isPassed) Color.parseColor("#FFCDD2")
        else if (subjects.indexOf(entry) % 2 == 0) Color.parseColor("#F5F5F5")
        else Color.WHITE
        row.setBackgroundColor(rowBg)

        fun tv(text: String, center: Boolean = false, bold: Boolean = false, color: Int = Color.BLACK) =
            TextView(this).also {
                it.text = text; it.setPadding(8, 12, 8, 12); it.textSize = 13f
                if (center) it.gravity = Gravity.CENTER
                if (bold) it.setTypeface(null, Typeface.BOLD)
                it.setTextColor(color)
            }

        val gradeColor = if (isPassed) Color.parseColor("#2E7D32") else Color.RED
        row.addView(tv(entry.name))
        row.addView(tv(entry.obtained.toString(), center = true))
        row.addView(tv(entry.total.toString(), center = true))
        row.addView(tv(entry.grade, center = true, bold = true, color = gradeColor))
        gradeTable.addView(row)
    }

    private fun calculateGPAValue(): Double {
        if (subjects.isEmpty()) return 0.0
        return subjects.sumOf { gradeToGPA(it.grade) } / subjects.size
    }

    private fun updateGPA() {
        val gpa = calculateGPAValue()
        val passed = subjects.count { it.grade != "F" }
        val failed = subjects.size - passed
        gpaText.text = "GPA: ${"%.2f".format(gpa)}"
        summaryText.text = "Subjects: ${subjects.size} | Passed: $passed | Failed: $failed"
    }
}