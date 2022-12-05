package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.room.Room

class TodoRepository(private val dao: TodoDao) {

    val allTodos: LiveData<List<TodoEntity>> = dao.all

    fun addTodo(todo: TodoEntity) {
        dao.insert(todo)
    }

    fun updateTodo(todo: TodoEntity) {
        dao.update(todo)
    }

    fun getById(id: Long): LiveData<TodoEntity> {
        return dao.getById(id)
    }
}