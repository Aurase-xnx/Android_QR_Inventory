package com.example.dilhan_eser_project

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class InventoryManagementActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory_management)

        var inventoryAddButton = findViewById<Button>(R.id.inventorymgmt_Add)
        var inventoryListView = findViewById<ListView>(R.id.inventorymgmt_list_view)

        val dbHelper = InventoryDBHelper(this@InventoryManagementActivity)
        val db = dbHelper.readableDatabase
        val createTableSql = "CREATE TABLE IF NOT EXISTS inventory (_id INTEGER PRIMARY KEY AUTOINCREMENT,type TEXT CHECK (type IN ('tablette', 'smartphone')),marque_et_modele TEXT,identifiant TEXT UNIQUE,url_fabricant TEXT,qrcode BLOB)"
        db.execSQL(createTableSql)
        val cursor = db.query("inventory", null, null, null, null, null, null)
        val count = cursor.count

        val adapter = SimpleCursorAdapter(this@InventoryManagementActivity, R.layout.inventory_list_schema, cursor, arrayOf("_id","type", "marque_et_modele","identifiant","url_fabricant","qrcode"), intArrayOf(R.id.inventorymgmt_item_text1,R.id.inventorymgmt_item_text2,R.id.inventorymgmt_item_text3,R.id.inventorymgmt_item_text4,R.id.inventorymgmt_item_text5,R.id.inventorymgmt_item_text6), 0)
        inventoryListView.adapter = adapter

        inventoryAddButton.setOnClickListener(){
            val intent = Intent(this, InventoryItemCreator::class.java)
            startActivity(intent)
        }
    }
}