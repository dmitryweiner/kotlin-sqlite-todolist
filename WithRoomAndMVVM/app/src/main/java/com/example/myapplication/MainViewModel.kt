package com.example.myapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class MainViewModel(application: Application) : AndroidViewModel(application) {
    val repository: TodoRepository
    val allTodos: LiveData<List<TodoEntity>>

    init {
        val dao = TodoDatabase.getDatabase(application).todoDao()
        repository = TodoRepository(dao)
        allTodos = repository.allTodos
    }

    fun addTodo(todo: TodoEntity) {
        repository.addTodo(todo)
    }
}