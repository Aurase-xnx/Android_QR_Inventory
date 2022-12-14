package com.example.dilhan_eser_project

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class CreditsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_credits)

        val imgXnX = findViewById<ImageButton>(R.id.credits_img)
        imgXnX.setOnClickListener(){
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Aurase-xnx"))
            startActivity(browserIntent)
        }
        val username = intent.getStringExtra("username")
        val password = intent.getStringExtra("password")

        Toast.makeText(this, username + password,Toast.LENGTH_SHORT).show()
    }
}