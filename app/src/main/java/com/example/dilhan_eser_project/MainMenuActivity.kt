package com.example.dilhan_eser_project

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainMenuActivity : AppCompatActivity() {
    private lateinit var mUsername: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainmenu)

        mUsername = findViewById(R.id.mainmenu_username)

        val username = intent.getStringExtra("username")
        val formattedString = String.format("Bonjour %s", username)
        mUsername.text = formattedString
        if (username == "Admin"){
            val button = findViewById<Button>(R.id.mainmenu_Admin)
            button.visibility = View.VISIBLE
        }
    }
}