package com.example.nativerentalz


import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

/**
 * Let's start by creating our database CRUD helper class
 * based on the SQLiteHelper.
 */
class SQLHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    // Initialize database
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE $TABLE_NAME (ID INTEGER PRIMARY KEY " +
                    "AUTOINCREMENT, NAME TEXT, ADDRESS TEXT, TYPE TEXT, FURNITURE TEXT, BEDROOM TEXT, PRICE TEXT, DATE TEXT, REPORTER TEXT, NOTES TEXT)"
        )
    }

    // Upgrade: Destroy then create the database
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // Insert Table
    fun insertData(
        name: String,
        address: String,
        type: String,
        furniture: String,
        bedroom: String,
        price: String,
        date: String,
        reporter: String,
        notes: String,
    ) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_2, name)
        contentValues.put(COL_3, address)
        contentValues.put(COL_4, type)
        contentValues.put(COL_5, furniture)
        contentValues.put(COL_6, bedroom)
        contentValues.put(COL_7, price)
        contentValues.put(COL_8, date)
        contentValues.put(COL_9, reporter)
        contentValues.put(COL_10, notes)
        db.insert(TABLE_NAME, null, contentValues)
    }

    // Update Table
    fun updateData(
        id: String,
        name: String,
        address: String,
        type: String,
        furniture: String,
        bedroom: String,
        price: String,
        date: String,
        reporter: String,
        notes: String
    ):
            Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_1, id)
        contentValues.put(COL_2, name)
        contentValues.put(COL_3, address)
        contentValues.put(COL_4, type)
        contentValues.put(COL_5, furniture)
        contentValues.put(COL_6, bedroom)
        contentValues.put(COL_7, price)
        contentValues.put(COL_8, date)
        contentValues.put(COL_9, reporter)
        contentValues.put(COL_10, notes)
        db.update(TABLE_NAME, contentValues, "ID = ?", arrayOf(id))
        return true
    }

    /**
     * Let's create a function to delete a given row based on the id.
     */
    fun deleteData(id: String): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME, "ID = ?", arrayOf(id))
    }

    /**
     * The below getter property will return a Cursor containing our dataset.
     */
    val allData: Cursor
        get() {
            val db = this.writableDatabase
            return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        }

    /**
     * Let's create a companion object to hold our static fields.
     * A Companion object is an object that is common to all instances of a given
     * class.
     */
    companion object {
        val DATABASE_NAME = "rentalz.db"
        val TABLE_NAME = "properties"
        val COL_1 = "ID"
        val COL_2 = "NAME"
        val COL_3 = "ADDRESS"
        val COL_4 = "TYPE"
        val COL_5 = "FURNITURE"
        val COL_6 = "BEDROOM"
        val COL_7 = "PRICE"
        val COL_8 = "DATE"
        val COL_9 = "REPORTER"
        val COL_10 = "NOTES"
    }
}