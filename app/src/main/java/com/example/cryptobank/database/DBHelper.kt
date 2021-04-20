package com.example.cryptobank.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import java.util.*
import kotlin.collections.ArrayList
import com.example.cryptobank.datamodel.Currency

class DBHelper(var context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME,
    null, DATABASE_VER
) {
    companion object {
        private val DATABASE_VER = 1
        private val DATABASE_NAME = "main.db"

        private val TABLE_NAME = "FavoriteCurrencies"
        private val COL_ID = "userID"
        private val COL_CURRENCY = "userName"
//        private val COL_PASSWORD = "password"
//        private val COL_USER_SCORE = "userScore"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_QUERY =
            ("CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_CURRENCY TEXT UNIQUE )")
        db!!.execSQL(CREATE_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db!!)
    }


//    fun findUserByName(username: String): Boolean {
//        val db = this.readableDatabase
//        val query = "Select * from $TABLE_NAME WHERE $CI LIKE \"$username\""
//        val result = db.rawQuery(query, null)
//        if (result.moveToFirst()) {
//
//            if (result.getString(result.getColumnIndex(COL_USERNAME)) == username) {
//                db.close()
//                return true
//            }
//        }
//        db.close()
//        return false
//    }


//    fun updateScore(username: String, score: Int) {
//
//        val db = this.readableDatabase
//        val newValues = ContentValues()
//        newValues.put(COL_USERNAME, username)
//        newValues.put(COL_USER_SCORE, score.toString())
//
//        db.update(TABLE_NAME, newValues, "$COL_USERNAME='$username'", null)
//
//        db.close()
//        getAllUsers()
//    }

//    fun checkPassword(username: String, password: String): Boolean {
//        val db = this.readableDatabase
//        val query =
//            "Select * from $TABLE_NAME WHERE $COL_USERNAME LIKE \"$username\" AND $COL_PASSWORD LIKE \"$password\""
//        val result = db.rawQuery(query, null)
//        if (result.moveToFirst()) {
//            if ((result.getString(result.getColumnIndex(COL_USERNAME)) == username) &&
//                (result.getString(result.getColumnIndex(COL_PASSWORD)) == password)
//            ) {
//
//                db.close()
//                return true
//            }
//        }
//        db.close()
//        return false
//    }


//    fun getActualScore(username: String): Int {
//        val query = "SELECT * from $TABLE_NAME WHERE $COL_USERNAME LIKE \"$username\" "
//
//        val db = this.writableDatabase
//
//        val result = db.rawQuery(query, null)
//        if (result.moveToFirst()) {
//            val output = result.getInt(result.getColumnIndex(COL_USER_SCORE))
//            return output
//        }
//        return 0;
//    }


    fun getFavoriteCurrencies(): MutableList<Currency> {
        val list: MutableList<Currency> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $TABLE_NAME ORDER BY $COL_ID DESC LIMIT 10"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val currency = Currency(result.getString(result.getColumnIndex(COL_CURRENCY)), "0")
                list.add(currency)
            } while (result.moveToNext())
        }
        db.close()
        return list
    }

    fun addCurrency(currency: Currency) {
        val db = this.writableDatabase
        val values = ContentValues()
//        values.put(COL_ID,null)
        values.put(COL_CURRENCY, currency.currency)
//        values.put(COL_USERNAME, user.userName)
//        values.put(COL_PASSWORD, user.password)
//        values.put(COL_USER_SCORE, user.userScore)

        var result = db.insert(TABLE_NAME, null, values)
        if (result == (0).toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
        db.close()
    }


}