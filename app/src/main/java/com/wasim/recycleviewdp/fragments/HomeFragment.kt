package com.wasim.demobottomnavigation.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Build.ID
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

import com.wasim.recycleviewdp.R
import com.wasim.recycleviewdp.Todo
import com.wasim.recycleviewdp.TodoAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_fetch_data.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.userinfo_popup.view.*

class HomeFragment : Fragment() {
    //    this is database referance
    var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    lateinit var todoAdapter: TodoAdapter
    val todoList = arrayListOf<Todo>()
    private val itemTouchClick = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = false

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val referece = database!!.getReference("Data")
            val curentdate = referece.child("03-04-2021")
            curentdate.child(viewHolder.adapterPosition.toString()).removeValue()
            todoList.removeAt(viewHolder.adapterPosition)
            todoAdapter.notifyDataSetChanged()
            curentdate.setValue(todoList)

            //adapter.notifyItemChanged(viewHolder.adapterPosition)
        }
    }



    private var userId: String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        database?.setPersistenceEnabled(true)

        val referece = database!!.getReference("Data")
        val curentdate = referece.child("03-04-2021")

        curentdate.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                todoList.clear()
                snapshot.children.forEach {
                    val todo = it.getValue(Todo::class.java)
                    todoList.add(todo!!)
                }
                todoAdapter = TodoAdapter(HomeFragment.ctx!!, todoList as ArrayList<Todo>)
                if (isAdded) {
                    rvTodos.adapter = todoAdapter
                    rvTodos.layoutManager = LinearLayoutManager(HomeFragment.ctx!!)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("error", error.toString())
            }

        })
        val callback = ItemTouchHelper(itemTouchClick)
        callback.attachToRecyclerView(rvTodos)
        btnAddtodo.setOnClickListener {
            showPopUp()
        }
        btn_clear.setOnClickListener {
            todoList.clear()
            todoAdapter.notifyDataSetChanged()
        }
        btn_send.setOnClickListener {
            curentdate.setValue(todoList )
        }
    }

    //    fun hideKeyboard(view: View) {
//        val inputMethodManager =
//            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
//        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
//    }
    fun showPopUp() {
        val view = layoutInflater.inflate(R.layout.userinfo_popup, null)
        val builder = AlertDialog.Builder(ctx!!)
//        builder.setTitle("Add New Data")
        builder.setView(view)
        val dialog = builder.create()
        dialog.show()
        view.iv_close.setOnClickListener {
            dialog.dismiss()
        }

        view.btn_add.setOnClickListener {
            val name = view.et_name.text.toString()
            val amount = view.et_amount.text.toString().toInt()
            val todo = Todo(name, amount)
            todoList.add(todo)
            todoAdapter.notifyDataSetChanged()
            todoAdapter.notifyItemInserted(todoList.size - 1)
            rvTodos.scrollToPosition(todoList.size - 1)
            dialog.dismiss()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context

    }

    companion object {
        var ctx: Context? = null
    }

}