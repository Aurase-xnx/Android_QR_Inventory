package com.example.dilhan_eser_project

import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class InventoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory)

        var inventoryId = findViewById<TextView>(R.id.inventory_id)
        var inventoryName = findViewById<TextView>(R.id.inventory_HardwareName)
        var inventoryDescription = findViewById<TextView>(R.id.inventory_Description)
        var inventoryStatus = findViewById<Switch>(R.id.inventory_Status)
        val inventoryDeleteItem = findViewById<ImageButton>(R.id.inventory_Delete)
        val inventoryModItem = findViewById<ImageButton>(R.id.inventory_Modify)
        var inventoryNameAdd = findViewById<EditText>(R.id.inventory_HardwareNameAdd)
        var inventoryDescriptionAdd = findViewById<EditText>(R.id.inventory_DescriptionAdd)
        val inventoryAdd = findViewById<ImageButton>(R.id.inventory_Add)

        val dbHelper = InventoryDBHelper(this@InventoryActivity)
        val db = dbHelper.readableDatabase
        val createTableSql = "CREATE TABLE IF NOT EXISTS inventory (itemName TEXT PRIMARY KEY, description TEXT, qrCode TEXT)"
        db.execSQL(createTableSql)
        val cursor = db.query("inventory", null, null, null, null, null, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val name = cursor.getString(cursor.getColumnIndexOrThrow("itemName"))
                val description = cursor.getString(cursor.getColumnIndexOrThrow("description"))
                val status = cursor.getInt(cursor.getColumnIndexOrThrow("status"))

                inventoryId.setText(id.toString())
                inventoryName.setText(name)
                inventoryDescription.setText(description)
                inventoryStatus.setText(status)
            } while (cursor.moveToNext())
        }
    }
}