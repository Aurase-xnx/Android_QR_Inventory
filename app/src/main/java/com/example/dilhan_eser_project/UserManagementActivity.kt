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

        val dbHelper = UserDBHelper(this@UserManagementActivity)
        val db = dbHelper.readableDatabase
        val cursor = db.query("login", null, null, null, null, null, null)
        val count = cursor.count
        Log.d("TableActivity", "Cursor count: $count")

        val adapter = SimpleCursorAdapter(this@UserManagementActivity, R.layout.user_list_schema, cursor, arrayOf("_id","username", "password"), intArrayOf(R.id.usermgmt_item_text1,R.id.usermgmt_item_text2,R.id.usermgmt_item_text3), 0)
        userListView.adapter = adapter

        userListView.setOnItemClickListener { parent, view, position, id ->
        val item = parent.getItemAtPosition(position)
        Log.d("position","= $position")
        val searchedId = position+1
        val cursor = db.query("login",null,"_id = ?", arrayOf(searchedId.toString()),null,null,null)
            if (cursor.moveToFirst()){
                val username = cursor.getString(cursor.getColumnIndexOrThrow("username"))
                val password = cursor.getString(cursor.getColumnIndexOrThrow("password"))

                val delModDialog = AlertDialog.Builder(this)
                    .setMessage(username)
                    .setTitle("Voulez vous modifier ou supprimer cet utilisateur ?")
                    .setPositiveButton("Modifier"){ dialog, _ ->
                        val intent = Intent(this,CreditsActivity::class.java)
                        intent.putExtra("username",username)
                        intent.putExtra("password",password)
                        startActivity(intent)
                    }
                    .setNegativeButton("Supprimer"){dialog, _ ->
                        val intent = Intent(this,MainMenuActivity::class.java)
                        intent.putExtra("username",username)
                        intent.putExtra("password",password)
                        startActivity(intent)
                    }
                    .create()
                delModDialog.show()
            }
        }
    }
}