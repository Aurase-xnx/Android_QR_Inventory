package com.example.dilhan_eser_project

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SigninActivity : AppCompatActivity() {

    private lateinit var SUsernameField: EditText
    private lateinit var SPasswordField: EditText
    private lateinit var SSignupButton: Button
    private lateinit var SLoginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        SUsernameField = findViewById(R.id.signin_username)
        SPasswordField = findViewById(R.id.signin_password)
        SSignupButton = findViewById(R.id.signin_signBT)
        SLoginButton = findViewById(R.id.signin_logBT)
        val adminUser: String = intent.getStringExtra("adminUser").toString()
        Log.d("adminUser","$adminUser")
        if (adminUser != null && adminUser.isNotEmpty()){
            SUsernameField.setText(adminUser)
            SLoginButton.visibility = View.INVISIBLE
        }
        else{
            SUsernameField.setText("")
        }


        SSignupButton.setOnClickListener {
            val username = SUsernameField.text.toString()
            val password = SPasswordField.text.toString()
            var rights = "Basic"
            Log.d("wasaw","wasaw : $rights")

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this@SigninActivity, "Please fill out all fields.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 4) {
                Toast.makeText(this@SigninActivity, "The password must be at least 4 characters long.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (username == "Admin"){
                rights = "S_U"
            }
            Toast.makeText(this@SigninActivity, "BG$rights", Toast.LENGTH_SHORT).show()
            val userDbHelper = UserDBHelper(this@SigninActivity)
            val db = userDbHelper.writableDatabase
            val createTableSql = "CREATE TABLE IF NOT EXISTS login (_id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT UNIQUE, password TEXT NOT NULL, rights TEXT NOT NULL)"
            db.execSQL(createTableSql)
            val cursor = db.query("login", arrayOf("username"), "username = ?", arrayOf(username), null, null, null)

            if (cursor.moveToFirst()) {
                Toast.makeText(this@SigninActivity, "This username is already taken", Toast.LENGTH_SHORT).show()
            } else {

                val values = ContentValues()
                values.put("username", username)
                values.put("password", password)
                values.put("rights",rights)
                db.insert("login", null, values)
                Toast.makeText(this@SigninActivity, "Inscription réussie", Toast.LENGTH_SHORT).show()
                // Démarrer l'activité de connexion
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            cursor.close()
        }
        SLoginButton.setOnClickListener(){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
