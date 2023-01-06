package com.example.dilhan_eser_project

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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

        val rights = intent.getStringExtra("rights")
        val username = intent.getStringExtra("username")
        Log.d("RightsOfUser","Rights of current user : $rights")

        val formattedString = String.format("Bonjour %s", username)
        mUsername.text = formattedString
        if (rights == "S_U"){
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
        val usernamee = intent.getStringExtra("username")
        val password = intent.getStringExtra("password")

        Toast.makeText(this, usernamee + password, Toast.LENGTH_SHORT).show()


    }
    override fun onBackPressed() {
        val confirm = AlertDialog.Builder(this)
            .setMessage("Êtes-vous sûr de vouloir vous déconnecter ?")
            .setTitle("Déconnexion")
            .setPositiveButton("Oui") { dialog, _ ->
                dialog.dismiss()
                finish()
            }
            .setNegativeButton("Non") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
        confirm.show()
    }

}