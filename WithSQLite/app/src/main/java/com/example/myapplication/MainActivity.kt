package com.example.myapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val list = mutableListOf<Task>()
    private val adapter = RecyclerAdapter(list)
    private val dbHelper = DBHelper(this)

    companion object {
        const val REQUEST_CODE = 1
        const val ITEM_KEY = "ITEM_KEY"
        const val ITEM_ID_KEY = "ITEM_ID_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list.addAll(dbHelper.getAllTasks())

        adapter.onItemClick = {
            // open EditActivity
            val intent = Intent(this, EditActivity::class.java)
            intent.putExtra(ITEM_KEY, list.get(it).title)
            intent.putExtra(ITEM_ID_KEY, it)
            startActivityForResult(intent, REQUEST_CODE)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val editText = findViewById<EditText>(R.id.editTextTextPersonName)
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val title = editText.text.toString()
            if (title.isNotBlank()) {
                editText.text.clear()
                val id = dbHelper.addTask(title)
                list.add(Task(id, title))
                adapter.notifyItemInserted(list.lastIndex)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val result = data?.getStringExtra(EditActivity.RESULT_KEY)
            val id = data?.getIntExtra(ITEM_ID_KEY, 0)

            if (id != null && result != null) {
                // change list item
                list[id].title = result
                // redraw list
                adapter.notifyItemChanged(id)
                // update DB
                dbHelper.updateTask(list[id].id, result)
            }
        }
    }
}