package com.wasim.recycleviewdp

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var adapter: TodoAdapter
    val todoList = mutableListOf(
        Todo("Follow androidDevs", false),
        Todo("Follow androidDevs", false),
        Todo("Follow androidDevs", true),
        Todo("Follow androidDevs", false),
        Todo("Follow androidDevs", false),
        Todo("Follow androidDevs", true),
        Todo("Follow androidDevs", true)
    )
    private val itemTouchClick = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                (todoList as ArrayList<Todo>).removeAt(viewHolder.adapterPosition)
                adapter.notifyDataSetChanged()

                //adapter.notifyItemChanged(viewHolder.adapterPosition)
            }

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        adapter = TodoAdapter(this, todoList as ArrayList<Todo>)

        rvTodos.adapter = adapter
        rvTodos.layoutManager = LinearLayoutManager(this)
        val callback = ItemTouchHelper(itemTouchClick)
        callback.attachToRecyclerView(rvTodos)

        btnAddtodo.setOnClickListener {
            val title = editText.text.toString()
            val todo = Todo(title, false)
            todoList.add(todo)
            adapter.notifyDataSetChanged()
            adapter.notifyItemInserted(todoList.size - 1)
            rvTodos.scrollToPosition(todoList.size - 1)
            editText.setText("")
            hideKeyboard(editText)
        }

    }

    fun hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)


    }
}