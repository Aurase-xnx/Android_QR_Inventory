package com.example.dilhan_eser_project

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class InventoryDBHelper (context: Context) : SQLiteOpenHelper(context, "heh_inventory", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db?.execSQL("CREATE TABLE inventory (_id INTEGER PRIMARY KEY AUTOINCREMENT,type TEXT ,marque_et_modele TEXT,identifiant TEXT UNIQUE,url_fabricant TEXT,qrcode TEXT,isHere TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS inventory")
        onCreate(db)
    }
}