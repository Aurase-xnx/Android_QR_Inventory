package com.example.dilhan_eser_project

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class InventoryItemModActivity : AppCompatActivity(){
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory_item_mod)
        var itemId = intent.getStringExtra("itemId").toString()
        var id = itemId
        Log.d("itemId","itemId content : $itemId")
        val marqueEtModeleEdit = findViewById<EditText>(R.id.itemModifier_marqueEtModele)
        val identifiantEdit = findViewById<EditText>(R.id.itemModifier_identifiant)
        val isHereEdit = findViewById<Switch>(R.id.itemModifier_isHere)
        val modButton = findViewById<Button>(R.id.itemModifier_modifier)

        val dbHelper = UserDBHelper(this)
        val db = dbHelper.readableDatabase

        val cursor = db.query("inventory",null,"_id = ?", arrayOf(itemId.toString()),null,null,null)
        if (cursor.moveToNext()) {
            val type = cursor.getString(cursor.getColumnIndexOrThrow("type"))
            val marque_et_modele = cursor.getString(cursor.getColumnIndexOrThrow("marque_et_modele"))
            val identifiant = cursor.getString(cursor.getColumnIndexOrThrow("identifiant"))
            val url_fabricant = cursor.getString(cursor.getColumnIndexOrThrow("url_fabricant"))
            var isHere = cursor.getString(cursor.getColumnIndexOrThrow("isHere"))
            var isHereSwitch : Boolean
            if(isHere == "Remise"){
                isHereSwitch = false
                isHereEdit.isChecked = false
            }
            else{
                isHereSwitch = true
                isHereEdit.isChecked = true
            }

            marqueEtModeleEdit.setText(marque_et_modele)
            identifiantEdit.setText(identifiant)

            modButton.setOnClickListener(){
                val newMeM = marqueEtModeleEdit.text.toString()
                val newIdent = identifiantEdit.text.toString()
                val newIsHereSwitch = isHereEdit.isChecked
                val newIsHere : String
                if(newIsHereSwitch){
                    newIsHere = "Emprunté"
                }
                else{
                    newIsHere = "Remise"
                }
                if(newMeM.isEmpty() || newIdent.isEmpty()){
                    Toast.makeText(this,"Veuillez compléter les deux champs",Toast.LENGTH_SHORT).show()
                }
                if(newMeM != marque_et_modele){
                    val newValues = ContentValues()
                    newValues.put("marque_et_modele",newMeM)
                    newValues.put("isHere",newIsHere)
                    db.update("inventory",newValues,"_id = ?", arrayOf(id))
                    Toast.makeText(this,"Marque et modele modifié",Toast.LENGTH_SHORT).show()
                    finish()
                }
                if(newIdent != identifiant && newMeM == marque_et_modele){
                    val newValues = ContentValues()
                    newValues.put("identifiant",newIdent)
                    newValues.put("isHere",newIsHere)
                    db.update("inventory",newValues,"_id = ?", arrayOf(id))
                    Toast.makeText(this,"Identifiant modifié",Toast.LENGTH_SHORT).show()
                    finish()
                }
                if(newIdent != identifiant && newMeM != marque_et_modele){
                    val newValues = ContentValues()
                    newValues.put("marque_et_modele",newMeM)
                    newValues.put("identifiant",newIdent)
                    newValues.put("isHere",newIsHere)
                    db.update("inventory",newValues,"_id = ?", arrayOf(id))
                    Toast.makeText(this,"Identifiant modifié",Toast.LENGTH_SHORT).show()
                    finish()
                }
                if (newIsHere != isHere){
                    val db = dbHelper.writableDatabase
                    val newValues = ContentValues()
                    newValues.put("isHere", newIsHere)
                    db.update("inventory",newValues,"_id = ?", arrayOf(id))
                    finish()
                }
                else{
                    Toast.makeText(this,"Aucune modification observée",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}