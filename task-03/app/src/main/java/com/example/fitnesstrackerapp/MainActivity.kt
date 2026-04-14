package com.example.fitnesstrackerapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var stepsCount: TextView
    private lateinit var goalProgressBar: ProgressBar
    private lateinit var percentText: TextView
    private lateinit var updateButton: Button

    private val dailyGoal = 10000 // 10,000 steps
    private var currentSteps = 7500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        stepsCount = findViewById(R.id.stepsCount)
        goalProgressBar = findViewById(R.id.goalProgressBar)
        percentText = findViewById(R.id.percentText)
        updateButton = findViewById(R.id.updateButton)

        // Show initial progress
        updateProgress()

        // Update Stats Button
        updateButton.setOnClickListener {
            showUpdateDialog()
        }
    }

    private fun showUpdateDialog() {
        val input = EditText(this)
        input.hint = "Enter steps count"
        input.inputType = android.text.InputType.TYPE_CLASS_NUMBER
        input.setPadding(40, 20, 40, 20)

        AlertDialog.Builder(this)
            .setTitle("Update Steps")
            .setMessage("Enter today's step count:")
            .setView(input)
            .setPositiveButton("Update") { _, _ ->
                val entered = input.text.toString()
                if (entered.isNotEmpty()) {
                    currentSteps = entered.toInt()
                    updateProgress()
                } else {
                    Toast.makeText(this, "Please enter a number", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun updateProgress() {
        // Update steps card
        stepsCount.text = currentSteps.toString()

        // Calculate progress percentage
        val progress = ((currentSteps.toFloat() / dailyGoal) * 100).toInt().coerceAtMost(100)
        goalProgressBar.progress = progress
        percentText.text = "$progress%"

        // Show motivational toast if 100%
        if (progress >= 100) {
            Toast.makeText(this, "🎉 Amazing! You reached your daily goal!", Toast.LENGTH_LONG).show()
        }
    }
}