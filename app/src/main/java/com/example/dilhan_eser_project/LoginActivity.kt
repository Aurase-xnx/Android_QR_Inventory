package com.example.dilhan_eser_project

import android.content.Intent
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    private lateinit var usernameLI : EditText
    private lateinit var passwordLI : EditText
    private lateinit var loginLI : Button
    private lateinit var signinLI : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usernameLI = findViewById(R.id.login_username)
        passwordLI = findViewById(R.id.login_password)
        loginLI = findViewById(R.id.login_logBT)
        signinLI = findViewById(R.id.login_signBT)

        loginLI.setOnClickListener{
            if (usernameLI.text.isEmpty() || passwordLI.text.isEmpty()) {
                Toast.makeText(this, "Remplissez tous les champs.", Toast.LENGTH_SHORT).show()
            }

            val usernameLog = usernameLI.text.toString()
            val passwordLog = passwordLI.text.toString()

            val dbHelper = UserDBHelper(this@LoginActivity)
            val db = dbHelper.readableDatabase
            val cursor = db.query("login",null,"username = ? AND password = ?",
                arrayOf(usernameLog, passwordLog),null,null,null)

            if (cursor.moveToFirst()){
                val cursor = db.rawQuery("SELECT rights FROM login WHERE username = ?", arrayOf(usernameLog))
                if(cursor.moveToFirst()){
                    var rights = cursor.getString(cursor.getColumnIndexOrThrow("rights"))
                    val intent = Intent(this, MainMenuActivity::class.java)
                    Toast.makeText(this@LoginActivity,"Connexion réussie",Toast.LENGTH_SHORT).show()
                    intent.putExtra("rights", rights)
                    intent.putExtra("username",usernameLog)
                    startActivity(intent)
                    finish()
                }
                else{
                    Toast.makeText(this,"ERREUR",Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this@LoginActivity,"Erreur dans les informations introduites",Toast.LENGTH_SHORT).show()
            }

        }

        signinLI.setOnClickListener{
            val intent = Intent(this, SigninActivity::class.java)
            startActivity(intent)
        }
    }
}