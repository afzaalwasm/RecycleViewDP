package com.wasim.recycleviewdp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_todo.view.*
import java.util.*
import kotlin.collections.ArrayList

class TodoAdapter(
    var todos: ArrayList<Todo>,
//    heirorderfunction
    val onTodoItemLongClick: ((Todo) -> Unit)
) : RecyclerView.Adapter<TodoAdapter.TodoviewHolder>(), Filterable {

    var filterlist = ArrayList<Todo>()

    inner class TodoviewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoviewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return TodoviewHolder(view)
    }

    override fun getItemCount(): Int {
        return filterlist.size
    }

    //in onBindview fun  show all atem in it
    override fun onBindViewHolder(holder: TodoviewHolder, position: Int) {
        holder.itemView.apply {
            tvTitle.text = filterlist[position].name
            tv_amount.text = filterlist[position].amount.toString()
            setOnLongClickListener {
                onTodoItemLongClick.invoke(filterlist[position])
                //listener.onToDoItemClickListener(todos[position])
                true
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val charsearch = p0.toString()
                if (charsearch.isEmpty()) {
                    filterlist = todos as ArrayList<Todo>
                } else {
                    var resultList = ArrayList<Todo>()
                    for (row in todos) {
                        if (row.name?.toLowerCase()!!.contains(p0.toString().toLowerCase())) {
                            resultList.add(row)
                        }
                    }
                    filterlist = resultList
                }
                val filterResult = FilterResults()
                filterResult.values = filterlist
                return filterResult
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                filterlist = p1?.values as ArrayList<Todo>
                notifyDataSetChanged()
            }

        }
    }

    init {
        filterlist = todos as ArrayList<Todo>
    }

//    interface ToDoItemClickListeners{
//        fun onToDoItemClickListener(todo: Todo)
//    }


}