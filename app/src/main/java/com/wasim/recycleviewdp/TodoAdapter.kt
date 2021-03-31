package com.wasim.recycleviewdp

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_todo.view.*

class TodoAdapter(
    var todos: List<Todo>
) : RecyclerView.Adapter<TodoAdapter.TodoviewHolder>() {

    inner class TodoviewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoviewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return TodoviewHolder(view)
    }

    override fun getItemCount(): Int {
//        how many item in my listview
        return todos.size

    }

    override fun onBindViewHolder(holder: TodoviewHolder, position: Int) {
        holder.itemView.apply {
            tvTitle.text = todos[position].title
            cbDone.isChecked = todos[position].isChecked
        }

    }


}