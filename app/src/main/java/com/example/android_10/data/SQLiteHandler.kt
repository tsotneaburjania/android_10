package com.example.android_10.data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import kotlin.Int


class SQLiteHandler(context: Context): SQLiteOpenHelper(context,dbName,null,dbVersion) {
    companion object {
        private const val dbVersion = 1
        private const val dbName = "sqlite_database"
        private const val tableName = "event_records"
        private const val pKeyId = "id"
        private const val keyState = "state"
        private const val keyActionTime = "action_time"
        private const val keyActionType = "action_type"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        val createEventRecordsTable = ("CREATE TABLE " + tableName + "("
                + pKeyId + " INTEGER PRIMARY KEY," + keyState + " TEXT,"
                + keyActionTime + " TEXT," +  keyActionType + " TEXT" +")")
        db?.execSQL(createEventRecordsTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $tableName")
        onCreate(db)
    }

    fun addEvtRecord(evt: EvtRecord):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(pKeyId, evt.id)
        contentValues.put(keyState, evt.state)
        contentValues.put(keyActionTime,evt.actionTime )
        contentValues.put(keyActionType,evt.actionType )

        val success = db.insert(tableName, null, contentValues)

        db.close()
        return success
    }

    @SuppressLint("Range")
    fun getAllEvtRecords():List<EvtRecord>{
        val recordList:ArrayList<EvtRecord> = ArrayList<EvtRecord>()
        val selectQuery = "SELECT  * FROM $tableName"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id: Int
        var state: String
        var actionTime: String
        var actionType: String
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"))
                state = cursor.getString(cursor.getColumnIndex("state"))
                actionTime = cursor.getString(cursor.getColumnIndex("action_time"))
                actionType = cursor.getString(cursor.getColumnIndex("action_type"))
                val evt = EvtRecord(id = id, state = state.toInt(), actionTime = actionTime, actionType = actionType)
                recordList.add(evt)
            } while (cursor.moveToNext())
        }
        return recordList
    }
}