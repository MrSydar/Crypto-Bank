package com.example.cryptobank.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.cryptobank.datamodel.Currency

class DBHelper(var context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME,
    null, DATABASE_VER
) {
    companion object {
        private val DATABASE_VER = 3
        private val DATABASE_NAME = "main.db"

        private val TABLE_NAME = "FavoriteCurrencies"
        private val COL_ID = "favID"
        private val COL_CURRENCY = "usersCrypto"
        private val COL_EMAIL = "userEmail  "
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_QUERY =
            ("CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_CURRENCY TEXT UNIQUE, $COL_EMAIL TEXT )")
        db!!.execSQL(CREATE_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db!!)
    }

    fun deleteRowFromTable(email: String, currencyName: String) {
        this.readableDatabase.delete(
            TABLE_NAME,
            "$COL_EMAIL=? and $COL_CURRENCY=?",
            arrayOf(email, currencyName)
        )
    }

    fun getFavoriteCurrencies(userEmail: String): MutableList<Currency> {
        val list: MutableList<Currency> = ArrayList()
        val db = this.readableDatabase
        val query =
            "Select * from $TABLE_NAME WHERE $COL_EMAIL LIKE \"$userEmail\" ORDER BY $COL_ID DESC LIMIT 10"
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

    fun addCurrency(currency: Currency, userEmail: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_CURRENCY, currency.currency)
        values.put(COL_EMAIL, userEmail)

        var result = db.insert(TABLE_NAME, null, values)
        if (result == (0).toLong()) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
        db.close()
    }


}