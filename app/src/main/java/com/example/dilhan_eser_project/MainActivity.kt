package com.example.dilhan_eser_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbHelper = UserDBHelper(this@MainActivity)
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM login", null)
        if (cursor.moveToFirst()) {
            val count = cursor.getInt(0)
            if (count == 0) {
                // La table est vide, demandez à l'utilisateur de créer le premier utilisateur
                val alertDialog = AlertDialog.Builder(this).create()
                alertDialog.setTitle("Compte Admin nécéssaire avant de continuer !")
                alertDialog.setMessage("Veuillez créer le compte de l'administrateur")

                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK"
                ) { dialog, which ->
                    val intent = Intent(this,SigninActivity::class.java)
                    intent.putExtra("adminUser","Admin")
                    Toast.makeText(this,"Veuillez choisir un mot de passe pour le compte administrateur",Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                    finish()
                }
                alertDialog.show()
            }
        }
        cursor.close()

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