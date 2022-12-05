package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {

    private val adapter = RecyclerAdapter()
    val viewModel: MainViewModel by viewModels()

    companion object {
        const val ITEM_ID_KEY = "ITEM_ID_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter.onItemClick = { id ->
            // open EditActivity
            val intent = Intent(this, EditActivity::class.java)
            intent.putExtra(ITEM_ID_KEY, id)
            startActivity(intent)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        viewModel.allTodos.observe(this, Observer {
            adapter.updateList(it)
        })

        val editText = findViewById<EditText>(R.id.editTextTextPersonName)
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val title = editText.text.toString()
            if (title.isNotBlank()) {
                editText.text.clear()
                val todoEntity = TodoEntity();
                todoEntity.title = title
                thread {
                    viewModel.addTodo(todoEntity)
                }
            }
        }
    }
}