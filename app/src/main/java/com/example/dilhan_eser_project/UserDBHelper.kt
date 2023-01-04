package com.example.dilhan_eser_project

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class UserDBHelper (context: Context) : SQLiteOpenHelper(context, "heh_inventory", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db?.execSQL("CREATE TABLE login (_id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT UNIQUE, password TEXT NOT NULL)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS login")
        onCreate(db)
    }
}