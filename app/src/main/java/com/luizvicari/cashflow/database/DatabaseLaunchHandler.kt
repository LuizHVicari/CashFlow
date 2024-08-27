package com.luizvicari.cashflow.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.luizvicari.cashflow.entities.Launch

class DatabaseLaunchHandler(context: Context ): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION), DatabaseLaunchInterface {

    override fun getAllItems(): List<Launch> {
        val db = this.writableDatabase
        val query = db.query(TABLE_NAME, null, null, null, null, null, null)
        val launchList = mutableListOf<Launch>()

        while(query.moveToNext()) {
            launchList += Launch(
                query.getString(TYPE),
                query.getString(DETAIL),
                query.getFloat(VALUE),
                query.getString(DATE)
            )
        }

        query.close()

        return launchList
    }

    override fun getBalance(): Float {
        val db = this.writableDatabase
        val creditQuery = db.query(TABLE_NAME, null, "type='Credit' OR type='Crédito'", null, null, null, null)
        var score = 0.0.toFloat()

        while(creditQuery.moveToNext()){
             score += creditQuery.getFloat(VALUE)
        }

        val debitScore = db.query(TABLE_NAME, null, "type='Debit' or type='Débito'", null, null, null, null)

        while(debitScore.moveToNext()) {
            score -= debitScore.getFloat(VALUE)
        }

        creditQuery.close()
        debitScore.close()
        return score
    }

    fun oCreate(db: SQLiteDatabase) {
        onCreate(db)
    }

    override fun insertRegistration(
        type: String,
        details: String,
        value: Float,
        date: String
    ): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("type", type)
        values.put("details", details)
        values.put("value", value)
        values.put("date", date)
        return db.insert(TABLE_NAME, null, values) != (-1).toLong()
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE IF NOT EXISTS $TABLE_NAME (_id INTEGER PRIMARY KEY AUTOINCREMENT, type TEXT NOT NULL, details TEXT NOT NULL, value FLOAT NOT NULL, date TEXT NOT NULL )")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(
            "DROP TABLE IF EXISTS $TABLE_NAME"
        )
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "app.sqlite"
        private const val DATABASE_VERSION = 5
        private const val TABLE_NAME = "LAUNCH"
        private const val ID = 0
        private const val TYPE = 1
        private const val DETAIL = 2
        private const val VALUE = 3
        private const val DATE = 4
    }


}