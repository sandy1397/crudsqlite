package com.example.sqllite
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $TABLE_NAME (ID INTEGER PRIMARY KEY " +
                "AUTOINCREMENT,NAME TEXT,MOBILE TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertData(name:String, mobile:String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_2, name)
        contentValues.put(COL_3, mobile)
        db.insert(TABLE_NAME, null, contentValues)
    }

    val viewAllData :Cursor
        get(){
            val db = this.writableDatabase
            return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        }

    fun viewIdData(id: String):Cursor {
        val db = this.writableDatabase
        val selectQuery = "SELECT  * FROM $TABLE_NAME WHERE $COL_1 = ?"
        return db.rawQuery(selectQuery, arrayOf(id))
    }

    fun viewMobileData(mobile: String):Cursor {
        val db = this.writableDatabase
        val selectQuery = "SELECT  * FROM $TABLE_NAME WHERE $COL_3 = ?"
        return db.rawQuery(selectQuery, arrayOf(mobile))
    }

    fun updateData(id: String, name: String, mobile: String):
            Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_1, id)
        contentValues.put(COL_2, name)
        contentValues.put(COL_3, mobile)
        db.update(TABLE_NAME, contentValues, "ID = ?", arrayOf(id))
        return true
    }
    fun deleteData(id : String) : Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME,"ID = ?", arrayOf(id))
    }

    companion object {
        const val DATABASE_NAME = "listOfGuests.db"
        const val TABLE_NAME = "guests"
        const val COL_1 = "ID"
        const val COL_2 = "NAME"
        const val COL_3 = "MOBILE"
    }
}
