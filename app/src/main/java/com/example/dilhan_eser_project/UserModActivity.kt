package com.example.dilhan_eser_project

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class UserModActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usermod)
        var itemId = intent.getStringExtra("itemId").toString()
        var id = itemId
        Log.d("itemId","itemId content : $itemId")
        val userEdit = findViewById<EditText>(R.id.usermod_username)
        val passwordEdit = findViewById<EditText>(R.id.usermod_password)
        val modButton = findViewById<Button>(R.id.usermod_modify)
        val adminSwitch = findViewById<Switch>(R.id.usermod_admin)


        val dbHelper = UserDBHelper(this@UserModActivity)
        val db = dbHelper.readableDatabase

        val cursor = db.query("login",null,"_id = ?", arrayOf(itemId.toString()),null,null,null)
        if (cursor.moveToNext()) {
            val username = cursor.getString(cursor.getColumnIndexOrThrow("username"))
            val password = cursor.getString(cursor.getColumnIndexOrThrow("password"))
            var rights = cursor.getString(cursor.getColumnIndexOrThrow("rights"))
            var rightsSwitch : Boolean
            if (rights == "S_U"){
                rightsSwitch = true
                adminSwitch.isChecked = true
            }
            else{
                rightsSwitch = false
                adminSwitch.isChecked = false
            }
            userEdit.setText(username)
            passwordEdit.setText(password)



            modButton.setOnClickListener(){
                val newUserName = userEdit.text.toString()
                val newPassWord = passwordEdit.text.toString()
                val newAdminRightsSwitch = adminSwitch.isChecked
                val newAdminRights : String
                if (newAdminRightsSwitch){
                    newAdminRights = "S_U"
                }
                else{
                    newAdminRights = "Basic"
                }
                if (newUserName.isEmpty() || newPassWord.isEmpty()){
                    Toast.makeText(this,"Veuillez compléter les deux champs",Toast.LENGTH_SHORT).show()
                }
                if(newUserName != username){
                    val newValues = ContentValues()
                    newValues.put("username",newUserName)
                    newValues.put("rights",newAdminRights)
                    db.update("login",newValues,"_id = ?", arrayOf(id))
                    Toast.makeText(this,"Nom d'utilisateur modifié",Toast.LENGTH_SHORT).show()
                    finish()
                }
                if(newPassWord != password && newUserName == username){
                    val newValues = ContentValues()
                    newValues.put("password",newPassWord)
                    newValues.put("rights",newAdminRights)
                    db.update("login",newValues,"_id = ?", arrayOf(id))
                    Toast.makeText(this,"Mot de passe modifié",Toast.LENGTH_SHORT).show()
                    finish()
                }
                if (newUserName != username && newPassWord != password){
                    val db = dbHelper.writableDatabase
                    val newValues = ContentValues()
                    newValues.put("username",newUserName)
                    newValues.put("password",newPassWord)
                    newValues.put("rights", newAdminRights)
                    db.update("login",newValues,"_id = ?", arrayOf(id))
                    Toast.makeText(this,"Modifications enregistrées",Toast.LENGTH_SHORT).show()
                    finish()
                }
                if (newAdminRights != rights)
                {
                    val db = dbHelper.writableDatabase
                    val newValues = ContentValues()
                    newValues.put("rights", newAdminRights)
                    db.update("login",newValues,"_id = ?", arrayOf(id))
                    finish()
                }
                else{
                Toast.makeText(this,"Aucune modification observée",Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}