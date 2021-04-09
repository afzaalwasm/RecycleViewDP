package com.wasim.demobottomnavigation.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.wasim.recycleviewdp.R
import com.wasim.recycleviewdp.Todo
import com.wasim.recycleviewdp.TodoAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.userinfo_popup.*
import kotlinx.android.synthetic.main.userinfo_popup.view.*

class HomeFragment : Fragment(R.layout.fragment_home),
    View.OnClickListener {
    var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    lateinit var currentReference: DatabaseReference
    lateinit var todoAdapter: TodoAdapter

    //lateinit var listener: TodoAdapter.ToDoItemClickListeners

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentReference = database!!.getReference("Data").child("03-04-2021")
        //listener = this

        initializeRecyclerView()
        fetchAllData()
        setupClickListeners()

    }

    private fun setupClickListeners() {
        val callback = ItemTouchHelper(itemTouchClick)
        callback.attachToRecyclerView(rvTodos)
        btnAddtodo.setOnClickListener(this)
        btn_clear.setOnClickListener(this)
        btn_send.setOnClickListener {
            currentReference.setValue(todoList)
        }
    }

    private fun fetchAllData() {
        currentReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                todoList.clear()
                snapshot.children.forEach {
                    val todo = it.getValue(Todo::class.java)
                    todoList.add(todo!!)
                }
                todoAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("error", error.toString())
            }

        })
    }
//initialliza our list view
    private fun initializeRecyclerView() {
        todoAdapter = TodoAdapter(todoList, { todo ->
            val position = todoList.indexOf(todo)
            showPopUp(
                todo.name ?: "",
                todo.amount.toString(),
                "Update",
//                call hearorder function
                { name: String, value: Int ->
                    val todo = Todo(name, value)
                    todoList.set(position, todo)
                    todoAdapter.notifyDataSetChanged()
                    rvTodos.scrollToPosition(todoList.size - 1)
                })

        })
//    initialinze recyler view init
        rvTodos.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = todoAdapter
        }
    }

    //    fun hideKeyboard(view: View) {
//        val inputMethodManager =
//            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
//        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
//    }
    fun showPopUp(
        name: String = "",
        value: String = "",
        buttonText: String = "",
        onButtonClicked: ((String, Int) -> Unit)
    ) {
        val view = layoutInflater.inflate(R.layout.userinfo_popup, null)
        val builder = AlertDialog.Builder(requireActivity())
        view.et_name.setText(name)
        view.et_amount.setText(value)
        view.btn_add.setText(buttonText)
        builder.setView(view)
        val dialog = builder.create()
        dialog.show()
        view.iv_close.setOnClickListener {
            dialog.dismiss()
        }

        view.btn_add.setOnClickListener {
            val name = view.et_name.text.toString()
            val value = view.et_amount.text.toString().toInt()
            onButtonClicked.invoke(name, value)
            dialog.dismiss()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

//    override fun onToDoItemClickListener(todo: Todo) {
//
//    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnAddtodo -> showPopUp("", "", "Add", { name: String, value: Int ->
                val todo = Todo(name, value)
                todoList.add(todo)
                todoAdapter.notifyDataSetChanged()
                todoAdapter.notifyItemInserted(todoList.size - 1)
                rvTodos.scrollToPosition(todoList.size - 1)
            })
            R.id.btn_clear -> {
                todoList.clear()
                todoAdapter.notifyDataSetChanged()
            }
        }
    }

}