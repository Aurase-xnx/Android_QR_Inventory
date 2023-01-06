package com.example.dilhan_eser_project

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet.Layout


class UserManagementActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_management)

        var userListView = findViewById<ListView>(R.id.usermgmt_list_view)

        val reloadButton = findViewById<ImageButton>(R.id.usermgmt_reloadView)

        val dbHelper = UserDBHelper(this@UserManagementActivity)
        val db = dbHelper.readableDatabase
        val cursor = db.query("login", null, null, null, null, null, null)
        val count = cursor.count

        val adapter = SimpleCursorAdapter(this@UserManagementActivity, R.layout.user_list_schema, cursor, arrayOf("_id","username", "password","rights"), intArrayOf(R.id.usermgmt_item_text1,R.id.usermgmt_item_text2,R.id.usermgmt_item_text3,R.id.usermgmt_item_text4), 0)
        userListView.adapter = adapter

        userListView.setOnItemClickListener { parent, view, position, id ->
            val item = parent.getItemAtPosition(position)
            val itemId = id
            val cursor = db.query("login",null,"_id = ?", arrayOf(itemId.toString()),null,null,null)
            if (cursor.moveToNext()){
            val id = cursor.getString(cursor.getColumnIndexOrThrow("_id"))
            val username = cursor.getString(cursor.getColumnIndexOrThrow("username"))
            val password = cursor.getString(cursor.getColumnIndexOrThrow("password"))
            val rights = cursor.getString(cursor.getColumnIndexOrThrow("rights"))

            val delModDialog = AlertDialog.Builder(this)
                .setMessage(username)
                .setTitle("Voulez vous modifier ou supprimer cet utilisateur ?")
                .setPositiveButton("Modifier"){ dialog, _ ->
                    val intent = Intent(this,UserModActivity::class.java)
                    intent.putExtra("itemId",itemId.toString())
                    startActivity(intent)
                }
                .setNegativeButton("Supprimer"){dialog, _ ->
                    if (username != "Admin"){
                        val deletedUser = db.delete("login","_id = ?", arrayOf(itemId.toString()))
                        if (deletedUser > 0) {
                            // afficher un message de confirmation
                            Toast.makeText(this, "L'utilisateur " + username + " a été supprimé", Toast.LENGTH_SHORT).show()
                            recreate()
                        } else {
                            // afficher un message d'erreur
                            Toast.makeText(this, "Erreur lors de la suppression de l'enregistrement", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else{
                        Toast.makeText(this,"Vous ne pouvez pas supprimer le compte admin",Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                }
                .create()
            delModDialog.show()
            }
        }
        reloadButton.setOnClickListener(){
            recreate()
        }
    }
}