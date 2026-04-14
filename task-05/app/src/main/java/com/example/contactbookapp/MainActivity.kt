package com.example.contactbookapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var contactList: ListView
    private lateinit var searchView: SearchView
    private lateinit var emptyText: TextView
    private lateinit var fabAdd: com.google.android.material.floatingactionbutton.FloatingActionButton

    private val allContacts = mutableListOf<Contact>()
    private lateinit var adapter: ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        contactList = findViewById(R.id.contactList)
        searchView = findViewById(R.id.searchView)
        emptyText = findViewById(R.id.emptyText)
        fabAdd = findViewById(R.id.fabAdd)

        // Add sample contacts
        allContacts.addAll(listOf(
            Contact("Aktar Vai", "01711111111", "alice@email.com"),
            Contact("Bobbi Hajjaj", "01722222222", "bob@email.com"),
            Contact("Sakib", "01733333333", "carol@email.com"),
            Contact("Tawsif", "01744444444", "david@email.com"),
            Contact("Tareq Zia", "01755555555", "emma@email.com")
        ))

        adapter = ContactAdapter(this, allContacts)
        contactList.adapter = adapter
        checkEmpty()

        // FAB: add new contact
        fabAdd.setOnClickListener {
            showAddContactDialog()
        }

        // Item click: show details
        contactList.setOnItemClickListener { _, _, position, _ ->
            val contact = adapter.getItem(position)!!
            Toast.makeText(this,
                "Name: ${contact.name}\nPhone: ${contact.phone}\nEmail: ${contact.email}",
                Toast.LENGTH_LONG).show()
        }

        // Long press: delete
        contactList.setOnItemLongClickListener { _, _, position, _ ->
            val contact = adapter.getItem(position)!!
            AlertDialog.Builder(this)
                .setTitle("Delete Contact")
                .setMessage("Delete ${contact.name}?")
                .setPositiveButton("Delete") { _, _ ->
                    allContacts.remove(contact)
                    adapter.notifyDataSetChanged()
                    checkEmpty()
                }
                .setNegativeButton("Cancel", null)
                .show()
            true
        }

        // Search filter
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false
            override fun onQueryTextChange(text: String?): Boolean {
                val query = text.orEmpty().lowercase()
                val filtered = allContacts.filter {
                    it.name.lowercase().contains(query)
                }
                adapter.clear()
                adapter.addAll(filtered)
                adapter.notifyDataSetChanged()
                checkEmpty()
                return true
            }
        })
    }

    private fun showAddContactDialog() {
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 20, 40, 10)
        }
        val nameInput = EditText(this).apply { hint = "Full Name" }
        val phoneInput = EditText(this).apply {
            hint = "Phone Number"
            inputType = android.text.InputType.TYPE_CLASS_PHONE
        }
        val emailInput = EditText(this).apply {
            hint = "Email"
            inputType = android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        }
        layout.addView(nameInput)
        layout.addView(phoneInput)
        layout.addView(emailInput)

        AlertDialog.Builder(this)
            .setTitle("Add New Contact")
            .setView(layout)
            .setPositiveButton("Add") { _, _ ->
                val name = nameInput.text.toString().trim()
                val phone = phoneInput.text.toString().trim()
                val email = emailInput.text.toString().trim()
                if (name.isNotEmpty() && phone.isNotEmpty()) {
                    allContacts.add(Contact(name, phone, email))
                    adapter.notifyDataSetChanged()
                    checkEmpty()
                } else {
                    Toast.makeText(this, "Name and phone are required", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun checkEmpty() {
        emptyText.visibility = if (adapter.isEmpty) android.view.View.VISIBLE
        else android.view.View.GONE
    }
}