package com.wasim.recycleviewdp

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var todoList = mutableListOf(
                Todo("Follow androidDevs", false),
                Todo("Follow androidDevs", false),
                Todo("Follow androidDevs", true),
                Todo("Follow androidDevs", false),
                Todo("Follow androidDevs", false),
                Todo("Follow androidDevs", true),
                Todo("Follow androidDevs", true)

        )
        val adapter = TodoAdapter(todoList)
        rvTodos.adapter = adapter
        rvTodos.layoutManager = LinearLayoutManager(this)
        btnAddtodo.setOnClickListener {
            val title = editText.text.toString()
            val todo = Todo(title, false)
            todoList.add(todo)
            adapter.notifyDataSetChanged()
            adapter.notifyItemInserted(todoList.size - 1)
            rvTodos.scrollToPosition(todoList.size-1)
            editText.setText("")
            hideKeyboard(editText)
        }

    }

    fun hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}