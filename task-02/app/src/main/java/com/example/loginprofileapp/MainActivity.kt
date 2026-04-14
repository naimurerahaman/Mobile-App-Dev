package com.example.loginprofileapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Login form views
    private lateinit var loginForm: LinearLayout
    private lateinit var usernameInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var forgotPassword: TextView
    private lateinit var loginButton: Button
    private lateinit var progressBar: ProgressBar

    // Profile card views
    private lateinit var profileCard: LinearLayout
    private lateinit var logoutButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Connect views
        loginForm = findViewById(R.id.loginForm)
        usernameInput = findViewById(R.id.usernameInput)
        passwordInput = findViewById(R.id.passwordInput)
        forgotPassword = findViewById(R.id.forgotPassword)
        loginButton = findViewById(R.id.loginButton)
        progressBar = findViewById(R.id.progressBar)
        profileCard = findViewById(R.id.profileCard)
        logoutButton = findViewById(R.id.logoutButton)

        // Login button click
        loginButton.setOnClickListener {
            val username = usernameInput.text.toString()
            val password = passwordInput.text.toString()

            if (username == "admin" && password == "1234") {
                // Show progress bar, hide form
                loginForm.visibility = View.GONE
                progressBar.visibility = View.VISIBLE

                // After 1.5 seconds, hide progress and show profile
                Handler(Looper.getMainLooper()).postDelayed({
                    progressBar.visibility = View.GONE
                    profileCard.visibility = View.VISIBLE
                }, 1500)
            } else {
                Toast.makeText(this, "Invalid username or password!", Toast.LENGTH_SHORT).show()
            }
        }

        // Logout button click
        logoutButton.setOnClickListener {
            profileCard.visibility = View.GONE
            loginForm.visibility = View.VISIBLE
            usernameInput.text.clear()
            passwordInput.text.clear()
        }

        // Forgot password click
        forgotPassword.setOnClickListener {
            Toast.makeText(this, "Password reset link sent to your email", Toast.LENGTH_SHORT).show()
        }
    }
}