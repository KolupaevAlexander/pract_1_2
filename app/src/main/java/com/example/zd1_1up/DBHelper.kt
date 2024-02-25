package com.example.prakt1_2_v1

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.zd1_1up.ExpenseItem

class DBHelper (val context: Context):SQLiteOpenHelper(context,"consumption_database.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
       val quary = "CREATE TABLE consumption_database (id INT PRIMARY KEY, name TEXT, category TEXT, date TEXT, amount TEXT)"
        db!!.execSQL(quary)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS consumption_database")
        onCreate(db)
    }

    fun addConsumption(consumption: ExpenseItem)
    {
        val values = ContentValues()
        values.put("name", consumption.name)
        values.put("category", consumption.category)
        values.put("date", consumption.date)
        values.put("amount", consumption.amount)
        val dp = this.writableDatabase
        dp.insert("consumption_database", null, values)
        dp.close()
    }

    @SuppressLint("Range")
    fun readallConsumption(): ArrayList<ExpenseItem> {
        val movie = ArrayList<ExpenseItem>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from consumption_database", null)
        } catch (e: SQLiteException) {
            return ArrayList()
        }
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                var name = cursor.getString(cursor.getColumnIndex(DBContract.ConsumptionEntry.NAME)).toString()
                var category = cursor.getString(cursor.getColumnIndex(DBContract.ConsumptionEntry.CATEGORY)).toString()
                var date = cursor.getString(cursor.getColumnIndex(DBContract.ConsumptionEntry.DATE)).toString()
                var sum = cursor.getString(cursor.getColumnIndex(DBContract.ConsumptionEntry.AMOUNT)).toString()
                movie.add(ExpenseItem(name,category,date,sum))
                cursor.moveToNext()
            }
        }
        return movie
    }


    @Throws(SQLiteConstraintException::class)
    fun deleteConsumption(title: String): Boolean {
        val db = writableDatabase
        val selection = DBContract.ConsumptionEntry.NAME + " LIKE ?"
        val selectionArgs = arrayOf(title)
        db.delete(DBContract.ConsumptionEntry.TABLE_NAME, selection, selectionArgs)
        return true
    }

    @Throws(SQLiteConstraintException::class)
    fun getConsumption(title: String): Boolean {
        val db = this.readableDatabase
        val result = db.rawQuery("SELECT * FROM consumption_database WHERE name = '$title'",null)
        return result.moveToFirst()
    }

    fun deleteAllConsumption(): Boolean {
        val db = writableDatabase
        db.delete("consumption_database", null, null)
        return true
    }

    fun editConsumptionInfo(name: String, category: String, date: String, amount:String): Boolean
    {
        val db = writableDatabase
        val result = db.rawQuery("UPDATE consumption_database SET name = '$name', date='$date', category='$category', amount='$amount' WHERE name = '$name'",null)
        return result.moveToFirst()
    }



}