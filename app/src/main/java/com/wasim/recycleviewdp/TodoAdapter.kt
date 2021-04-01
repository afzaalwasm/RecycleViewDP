package com.wasim.recycleviewdp

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_todo.view.*
import java.util.ArrayList
import kotlin.math.log

class TodoAdapter(
    val ctx: Context,
    var todos: ArrayList<Todo>
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
            imageView.setImageResource(R.drawable.ic_launcher_background)
            tvTitle.setOnClickListener {
                Log.d("tvTitle", "onBindViewHolder: ${tvTitle.text}")
                Toast.makeText(ctx, "${tvTitle.text}", Toast.LENGTH_SHORT).show()
            }
//            tvTitle.setOnLongClickListener{
//                todos.removeAt(position)
//                notifyDataSetChanged()
//                Toast.makeText(ctx, "Long click detected", Toast.LENGTH_SHORT).show()
//                true
//            }


        }

    }


}