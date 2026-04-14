package com.example.studentregistrationapp

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    // ===== Declare all UI variables =====
    private lateinit var etStudentId: EditText
    private lateinit var etFullName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etAge: EditText

    private lateinit var radioGroupGender: RadioGroup
    private lateinit var rbMale: RadioButton
    private lateinit var rbFemale: RadioButton
    private lateinit var rbOther: RadioButton

    private lateinit var cbFootball: CheckBox
    private lateinit var cbCricket: CheckBox
    private lateinit var cbBasketball: CheckBox
    private lateinit var cbBadminton: CheckBox

    private lateinit var spinnerCountry: Spinner
    private lateinit var btnPickDate: Button
    private lateinit var tvSelectedDate: TextView

    private lateinit var btnSubmit: Button
    private lateinit var btnReset: Button

    // To store the selected date
    private var selectedDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ===== Connect all UI components using findViewById =====
        etStudentId     = findViewById(R.id.etStudentId)
        etFullName      = findViewById(R.id.etFullName)
        etEmail         = findViewById(R.id.etEmail)
        etPassword      = findViewById(R.id.etPassword)
        etAge           = findViewById(R.id.etAge)

        radioGroupGender = findViewById(R.id.radioGroupGender)
        rbMale          = findViewById(R.id.rbMale)
        rbFemale        = findViewById(R.id.rbFemale)
        rbOther         = findViewById(R.id.rbOther)

        cbFootball      = findViewById(R.id.cbFootball)
        cbCricket       = findViewById(R.id.cbCricket)
        cbBasketball    = findViewById(R.id.cbBasketball)
        cbBadminton     = findViewById(R.id.cbBadminton)

        spinnerCountry  = findViewById(R.id.spinnerCountry)
        btnPickDate     = findViewById(R.id.btnPickDate)
        tvSelectedDate  = findViewById(R.id.tvSelectedDate)

        btnSubmit       = findViewById(R.id.btnSubmit)
        btnReset        = findViewById(R.id.btnReset)

        // ===== Setup Spinner with country list =====
        val countries = listOf("Select Country", "Bangladesh", "India", "USA", "UK", "Canada")

        // ArrayAdapter puts the list into the spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, countries)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCountry.adapter = adapter

        // ===== Date Picker Button Click =====
        btnPickDate.setOnClickListener {
            showDatePicker()
        }

        // ===== Submit Button Click =====
        btnSubmit.setOnClickListener {
            submitForm()
        }

        // ===== Reset Button Click =====
        btnReset.setOnClickListener {
            resetForm()
        }
    }

    // ===== Function to open DatePickerDialog =====
    private fun showDatePicker() {

        // Get today's date to show in the picker by default
        val calendar = Calendar.getInstance()
        val year  = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day   = calendar.get(Calendar.DAY_OF_MONTH)

        // Create and show the DatePickerDialog
        val datePicker = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                // Month starts from 0, so we add 1
                selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                tvSelectedDate.text = "DOB: $selectedDate"
            },
            year, month, day
        )
        datePicker.show()
    }

    // ===== Function to collect data and validate =====
    private fun submitForm() {

        // --- Collect all input values ---
        val studentId = etStudentId.text.toString().trim()
        val fullName  = etFullName.text.toString().trim()
        val email     = etEmail.text.toString().trim()
        val password  = etPassword.text.toString().trim()
        val age       = etAge.text.toString().trim()

        // --- Get selected gender ---
        val selectedGenderId = radioGroupGender.checkedRadioButtonId
        val gender = if (selectedGenderId != -1) {
            findViewById<RadioButton>(selectedGenderId).text.toString()
        } else {
            ""
        }

        // --- Get selected sports (can be multiple) ---
        val sportsList = mutableListOf<String>()
        if (cbFootball.isChecked)   sportsList.add("Football")
        if (cbCricket.isChecked)    sportsList.add("Cricket")
        if (cbBasketball.isChecked) sportsList.add("Basketball")
        if (cbBadminton.isChecked)  sportsList.add("Badminton")
        val sports = sportsList.joinToString(", ") // e.g. "Football, Cricket"

        // --- Get selected country ---
        val country = spinnerCountry.selectedItem.toString()

        // ===== VALIDATION =====

        // Check if any field is empty
        if (studentId.isEmpty() || fullName.isEmpty() || email.isEmpty() ||
            password.isEmpty() || age.isEmpty()) {
            Toast.makeText(this, "Please complete all required fields", Toast.LENGTH_LONG).show()
            return
        }

        // Check age is greater than 0
        if (age.toInt() <= 0) {
            Toast.makeText(this, "Please complete all required fields", Toast.LENGTH_LONG).show()
            return
        }

        // Check email contains "@"
        if (!email.contains("@")) {
            Toast.makeText(this, "Please complete all required fields", Toast.LENGTH_LONG).show()
            return
        }

        // Check gender is selected
        if (gender.isEmpty()) {
            Toast.makeText(this, "Please complete all required fields", Toast.LENGTH_LONG).show()
            return
        }

        // Check country is selected (not the default "Select Country")
        if (country == "Select Country") {
            Toast.makeText(this, "Please complete all required fields", Toast.LENGTH_LONG).show()
            return
        }

        // Check date is selected
        if (selectedDate.isEmpty()) {
            Toast.makeText(this, "Please complete all required fields", Toast.LENGTH_LONG).show()
            return
        }

        // ===== SHOW TOAST WITH ALL DATA =====
        val message = """
            ID: $studentId
            Name: $fullName
            Email: $email
            Age: $age
            Gender: $gender
            Sports: $sports
            Country: $country
            DOB: $selectedDate
        """.trimIndent()

        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    // ===== Function to reset all fields =====
    private fun resetForm() {

        // Clear all EditText fields
        etStudentId.setText("")
        etFullName.setText("")
        etEmail.setText("")
        etPassword.setText("")
        etAge.setText("")

        // Clear selected RadioButton
        radioGroupGender.clearCheck()

        // Uncheck all CheckBoxes
        cbFootball.isChecked   = false
        cbCricket.isChecked    = false
        cbBasketball.isChecked = false
        cbBadminton.isChecked  = false

        // Reset Spinner to first item
        spinnerCountry.setSelection(0)

        // Reset date
        selectedDate = ""
        tvSelectedDate.text = "No date selected"

        Toast.makeText(this, "Form has been reset!", Toast.LENGTH_SHORT).show()
    }
}