package com.example.dilhan_eser_project

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class InventoryItemCreator : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory_item_creator)

        var marqueModel = findViewById<EditText>(R.id.itemCreator_marqueEtModele)
        var identifiant = findViewById<EditText>(R.id.itemCreator_identifiant)
        var qrCode = findViewById<EditText>(R.id.itemCreator_qrCode)
        var createItemButton = findViewById<Button>(R.id.itemCreator_creator)


        val typeChoice = findViewById<Spinner>(R.id.itemCreator_type)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.type_choice,
            android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        typeChoice.adapter = adapter


        createItemButton.setOnClickListener(){
            val selectedType = typeChoice.selectedItem.toString()
            val marqueEtModele = marqueModel.text.toString()
            val identifiantUnique = identifiant.text.toString()
            var urlFabricant : String
            if (marqueEtModele == "LG Nexus 5"){
                urlFabricant = "https://www.lg.com/"
            }
            else if (marqueEtModele == "Samsung Nexus 10"){
                urlFabricant = "https://www.samsung.com/be_fr/"
            }
            else{
                urlFabricant = ""
            }
            val qrCodeContent = qrCode.text.toString()
            val userDbHelper = UserDBHelper(this@InventoryItemCreator)
            val db = userDbHelper.writableDatabase
            val createTableSql = "CREATE TABLE IF NOT EXISTS inventory (_id INTEGER PRIMARY KEY AUTOINCREMENT,type TEXT ,marque_et_modele TEXT,identifiant TEXT UNIQUE,url_fabricant TEXT,qrcode TEXT,isHere TEXT)"
            db.execSQL(createTableSql)
            val cursor = db.query("inventory", arrayOf("identifiant"), "identifiant = ?", arrayOf(identifiantUnique), null, null, null)

            if (cursor.moveToFirst()) {
                Toast.makeText(this@InventoryItemCreator, "Cet Identifiant unique existe déjà", Toast.LENGTH_SHORT).show()
            } else {
                Log.d("Type","Type $selectedType")
                val values = ContentValues()
                values.put("type",selectedType)
                values.put("marque_et_modele",marqueEtModele)
                values.put("identifiant",identifiantUnique)
                values.put("url_fabricant",urlFabricant)
                values.put("qrcode",qrCodeContent)
                values.put("isHere","Remise")
                db.insert("inventory",null,values)
                Toast.makeText(this@InventoryItemCreator,"Ajout du nouvel appareil confirmé",Toast.LENGTH_SHORT).show()
                finish()
            }
            cursor.close()
        }
    }

}
