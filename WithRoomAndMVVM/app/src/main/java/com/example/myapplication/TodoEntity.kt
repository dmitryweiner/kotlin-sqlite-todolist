package com.example.myapplication

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "todos")
class TodoEntity {
   @PrimaryKey(autoGenerate = true)
   var id: Long? = null
   var title: String = ""
   var isDone = false
}