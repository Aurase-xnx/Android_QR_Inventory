package com.example.dilhan_eser_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val main_loginBT = findViewById<Button>(R.id.main_loginBT)
        main_loginBT.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        val credits = findViewById<Button>(R.id.main_credits)
        credits.setOnClickListener(){
            val intent = Intent(this, CreditsActivity::class.java)
            startActivity(intent)
        }
    }

}