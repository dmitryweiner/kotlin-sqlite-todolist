package com.example.myapplication

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class EditActivity : AppCompatActivity() {
    var id = 0

    companion object {
        const val RESULT_KEY = "RESULT_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val itemText = intent.getStringExtra(MainActivity.ITEM_KEY)
        id = intent.getIntExtra(MainActivity.ITEM_ID_KEY, 0)
        val editText = findViewById<EditText>(R.id.editTextTextMultiLine)
        editText.setText(itemText)

        val buttonSave = findViewById<Button>(R.id.buttonSave)
        buttonSave.setOnClickListener {
            val returnIntent = Intent()
            returnIntent.putExtra(RESULT_KEY, editText.text.toString())
            returnIntent.putExtra(MainActivity.ITEM_ID_KEY, id)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }
}