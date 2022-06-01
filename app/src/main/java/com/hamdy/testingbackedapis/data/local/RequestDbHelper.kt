package com.hamdy.testingbackedapis.data.local

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.hamdy.testingbackedapis.data.local.RequestContract.RequestEntry.EXECUTION_TIME
import com.hamdy.testingbackedapis.data.local.RequestContract.RequestEntry.HEADERS
import com.hamdy.testingbackedapis.data.local.RequestContract.RequestEntry.REQUEST_METHODE
import com.hamdy.testingbackedapis.data.local.RequestContract.RequestEntry.RESPONSE
import com.hamdy.testingbackedapis.data.local.RequestContract.RequestEntry.STATUS_CODE
import com.hamdy.testingbackedapis.data.local.RequestContract.RequestEntry.TABLE_NAME
import com.hamdy.testingbackedapis.data.local.RequestContract.RequestEntry.URL
import com.hamdy.testingbackedapis.domain.model.ParamsData
import com.hamdy.testingbackedapis.domain.model.RequestData


class RequestDbHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val SQL_CREATE_PETS_TABLE =
            ("CREATE TABLE " + TABLE_NAME + " ("
                    + RequestContract.RequestEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + REQUEST_METHODE + " TEXT NOT NULL, "
                    + RESPONSE + " TEXT, "
                    + HEADERS + " TEXT, "
                    + URL + " TEXT, "
                    + EXECUTION_TIME + " LONG NOT NULL, "
                    + STATUS_CODE + " INTEGER NOT NULL DEFAULT 0);")
        db.execSQL(SQL_CREATE_PETS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "shelter.db"
    }

    fun addRequest(request: RequestData) {
        val values = ContentValues()
        values.put(REQUEST_METHODE, request.requestMethod)
        values.put(STATUS_CODE, request.statusCode)
        values.put(EXECUTION_TIME, request.executionTime)
        values.put(RESPONSE, request.response)
        values.put(HEADERS, request.headers)
        values.put(URL, request.url)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }
    fun getRequestsByMethod(method:String) : MutableList<RequestData> {
        val list: MutableList<RequestData> = mutableListOf()

        val selectQuery = "SELECT  * FROM $TABLE_NAME where $REQUEST_METHODE='$method'"
        val db = this.readableDatabase
        try {
            val cursor: Cursor = db.rawQuery(selectQuery, null)
            try {
                if (cursor.moveToFirst()) {
                    do {
                        list.add(
                            RequestData(
                                statusCode = cursor.getInt(6),
                                requestMethod = cursor.getString(1),
                                url = cursor.getString(4)?:""
                            )
                        )
                    } while (cursor.moveToNext())
                }
            } finally {
                try {
                    cursor.close()
                } catch (ignore: Exception) {
                }
            }
        } finally {
            try {
                db.close()
            } catch (ignore: Exception) {
            }
        }
        return list
    }

    fun getAllRequests() : MutableList<RequestData> {
        val list: MutableList<RequestData> = mutableListOf()
        val selectQuery = "SELECT  * FROM $TABLE_NAME"
        val db = this.readableDatabase
        try {
            val cursor: Cursor = db.rawQuery(selectQuery, null)
            try {
                if (cursor.moveToFirst()) {
                    do {
                        list.add(
                            RequestData(
                                statusCode = cursor.getInt(6),
                                requestMethod = cursor.getString(1),
                                url = cursor.getString(4)?:""
                            )
                        )
                    } while (cursor.moveToNext())
                }
            } finally {
                try {
                    cursor.close()
                } catch (ignore: Exception) {
                }
            }
        } finally {
            try {
                db.close()
            } catch (ignore: Exception) {
            }
        }
        return list
    }
    fun getSortedRequestsByTime() : MutableList<RequestData> {
        val list: MutableList<RequestData> = mutableListOf()

        val selectQuery = "SELECT  * FROM $TABLE_NAME ORDER BY $EXECUTION_TIME ASC"
        val db = this.readableDatabase
        try {
            val cursor: Cursor = db.rawQuery(selectQuery, null)
            try {
                if (cursor.moveToFirst()) {
                    do {
                        list.add(
                            RequestData(
                                statusCode = cursor.getInt(6),
                                requestMethod = cursor.getString(1),
                                url = cursor.getString(4)?:""
                            )
                        )
                    } while (cursor.moveToNext())
                }
            } finally {
                try {
                    cursor.close()
                } catch (ignore: Exception) {
                }
            }
        } finally {
            try {
                db.close()
            } catch (ignore: Exception) {
            }
        }
        return list
    }
}