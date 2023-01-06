package com.example.dilhan_eser_project

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class InventoryManagementActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory_management)

        var inventoryAddButton = findViewById<Button>(R.id.inventorymgmt_Add)
        var inventoryRefresh = findViewById<ImageButton>(R.id.inventorymgmt_ReloadView)
        var inventoryListView = findViewById<ListView>(R.id.inventorymgmt_list_view)

        val dbHelper = InventoryDBHelper(this@InventoryManagementActivity)
        val db = dbHelper.readableDatabase
        val createTableSql =
            "CREATE TABLE IF NOT EXISTS inventory (_id INTEGER PRIMARY KEY AUTOINCREMENT,type TEXT,marque_et_modele TEXT,identifiant TEXT UNIQUE,url_fabricant TEXT,qrcode TEXT,isHere TEXT)"
        db.execSQL(createTableSql)
        val cursor = db.query("inventory", null, null, null, null, null, null)
        val count = cursor.count

        val adapter = SimpleCursorAdapter(
            this@InventoryManagementActivity,
            R.layout.inventory_list_schema,
            cursor,
            arrayOf(
                "_id",
                "type",
                "marque_et_modele",
                "identifiant",
                "url_fabricant",
                "qrcode",
                "isHere"
            ),
            intArrayOf(
                R.id.inventorymgmt_item_text1,
                R.id.inventorymgmt_item_text2,
                R.id.inventorymgmt_item_text3,
                R.id.inventorymgmt_item_text4,
                R.id.inventorymgmt_item_text5,
                R.id.inventorymgmt_item_text6,
                R.id.inventorymgmt_item_text7
            ),
            0
        )
        inventoryListView.adapter = adapter

        inventoryListView.setOnItemClickListener { parent, view, position, id ->
            val item = parent.getItemAtPosition(position)

            val itemId = id
            val cursor = db.query("inventory", null, "_id = ?", arrayOf(itemId.toString()), null, null, null)
            Toast.makeText(this, "$cursor", Toast.LENGTH_SHORT).show()
            if (cursor.moveToNext()) {
                val id = cursor.getString(cursor.getColumnIndexOrThrow("_id"))
                val type = cursor.getString(cursor.getColumnIndexOrThrow("type"))
                val marque_et_modele =
                    cursor.getString(cursor.getColumnIndexOrThrow("marque_et_modele"))
                val identifiant = cursor.getString(cursor.getColumnIndexOrThrow("identifiant"))
                val url_fabricant = cursor.getString(cursor.getColumnIndexOrThrow("url_fabricant"))
                val qrCode = cursor.getString(cursor.getColumnIndexOrThrow("qrcode"))
                val isHere = cursor.getString(cursor.getColumnIndexOrThrow("isHere"))

                val delModDialog = AlertDialog.Builder(this)
                    .setMessage(marque_et_modele +"Avec l'identifiant " + identifiant)
                    .setTitle("Voulez vous modifier ou supprimer cet appareil ?")
                    .setPositiveButton("Modifier"){ dialog, _ ->
                        val intent = Intent(this,InventoryItemModActivity::class.java)
                        intent.putExtra("itemId",itemId.toString())
                        startActivity(intent)
                    }
                    .setNegativeButton("Supprimer"){dialog, _ ->
                        val deletedItem = db.delete("inventory","_id = ?", arrayOf(itemId.toString()))
                        if (deletedItem > 0) {
                            Toast.makeText(this, "L'appareil a été supprimé", Toast.LENGTH_SHORT).show()
                            recreate()
                        } else {
                            Toast.makeText(this, "Erreur lors de la suppression de l'enregistrement", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .create()
                delModDialog.show()
            }

        }
        inventoryAddButton.setOnClickListener() {
            val intent = Intent(this, InventoryItemCreator::class.java)
            startActivity(intent)
        }
        inventoryRefresh.setOnClickListener() {
            recreate()
        }
    }
}