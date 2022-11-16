package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        // версия БД
        const val DATABASE_VERSION = 1
        // название БД
        const val DATABASE_NAME = "tasksdb"
        // название таблицы
        const val TABLE_NAME = "tasks"
        // названия полей
        const val KEY_ID = "id"
        const val KEY_TITLE = "title"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE $TABLE_NAME (
            $KEY_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $KEY_TITLE TEXT NOT NULL
            )""")
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun getAllTasks(): List<Task> {
        val result = mutableListOf<Task>()
        val database = this.writableDatabase
        val cursor: Cursor = database.query(
            TABLE_NAME, null, null, null,
            null, null, null
        )
        if (cursor.moveToFirst()) {
            val idIndex: Int = cursor.getColumnIndex(KEY_ID)
            val titleIndex: Int = cursor.getColumnIndex(KEY_TITLE)
            do {
                val todo = Task(
                    cursor.getLong(idIndex),
                    cursor.getString(titleIndex)
                )
                result.add(todo)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return result
    }

    fun getById(id: Long): Task? {
        var result: Task? = null
        val database = this.writableDatabase
        val cursor: Cursor = database.query(
            TABLE_NAME, null, "$KEY_ID = ?", arrayOf(id.toString()),
            null, null, null
        )
        if (cursor.moveToFirst()) {
            val idIndex: Int = cursor.getColumnIndex(KEY_ID)
            val titleIndex: Int = cursor.getColumnIndex(KEY_TITLE)
            result = Task(
                cursor.getLong(idIndex),
                cursor.getString(titleIndex),
            )
        }
        cursor.close()
        return result
    }

    fun addTask(title: String): Long {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_TITLE, title)
        val id = database.insert(TABLE_NAME, null, contentValues)
        close()
        return id
    }

    fun updateTask(id: Long, title: String) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_TITLE, title)
        database.update(TABLE_NAME, contentValues, "$KEY_ID = ?", arrayOf(id.toString()))
        close()
    }

    fun deleteTask(id: Long) {
        val database = this.writableDatabase
        database.delete(TABLE_NAME, "$KEY_ID = ?", arrayOf(id.toString()))
        close()
    }
}

