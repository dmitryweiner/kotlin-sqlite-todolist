package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TodoDao {
   @get:Query("SELECT * FROM todos")
   val all: LiveData<List<TodoEntity>>

   @Query("SELECT * FROM todos WHERE id = :id")
   fun getById(id: Long): LiveData<TodoEntity>

   @Insert
   fun insert(todo: TodoEntity): Long

   @Update
   fun update(todo: TodoEntity)

   @Delete
   fun delete(todo: TodoEntity)
}