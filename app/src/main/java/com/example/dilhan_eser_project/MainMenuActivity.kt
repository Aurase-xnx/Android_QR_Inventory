package com.example.dilhan_eser_project

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainMenuActivity : AppCompatActivity() {
    private lateinit var mUsername: TextView
    private lateinit var mAdmin: Button
    private lateinit var mInventory: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        mUsername = findViewById(R.id.mainmenu_username)
        mAdmin = findViewById(R.id.mainmenu_Admin)
        mInventory = findViewById(R.id.mainmenu_Inventory)

        val username = intent.getStringExtra("username")
        val formattedString = String.format("Bonjour %s", username)
        mUsername.text = formattedString
        if (username == "Admin"){
            val button = findViewById<Button>(R.id.mainmenu_Admin)
            button.visibility = View.VISIBLE
        }
        mAdmin.setOnClickListener(){
            val intent = Intent(this, UserManagementActivity::class.java)
            startActivity(intent)
        }
        mInventory.setOnClickListener(){
            val intent = Intent(this, InventoryActivity::class.java)
            startActivity(intent)
        }
    }
}