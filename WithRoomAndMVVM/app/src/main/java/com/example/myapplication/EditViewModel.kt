package com.example.myapplication

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class EditViewModel(application: Application) : AndroidViewModel(application) {
    val repository: TodoRepository
    var todo: LiveData<TodoEntity> = MutableLiveData()

    init {
        val dao = TodoDatabase.getDatabase(application).todoDao()
        repository = TodoRepository(dao)
    }

    fun loadTodo(id: Long) {
        todo = repository.getById(id)
    }

    fun saveTodo(title: String) {
        todo.value?.title = title
        todo?.value.let {
            repository.updateTodo(it!!)
        }
    }
}