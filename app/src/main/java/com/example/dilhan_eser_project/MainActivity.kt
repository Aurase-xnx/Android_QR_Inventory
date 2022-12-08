package com.example.dilhan_eser_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val main_loginBT = findViewById(R.id.main_loginBT) as Button
        main_loginBT.setOnClickListener{
            MainActivity.this.startActivity.
        }
    }

}