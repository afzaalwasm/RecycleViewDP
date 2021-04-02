package com.wasim.recycleviewdp

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.userinfo_popup.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var adapter: TodoAdapter
    val todoList = arrayListOf<Todo>(

//        Todo("Follow androidDevs", false),
//        Todo("Follow androidDevs", true),
//        Todo("Follow androidDevs", false),
//        Todo("Follow androidDevs", false),
//        Todo("Follow androidDevs", true),
//        Todo("Follow androidDevs", true)
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
    var database: FirebaseDatabase? = null
    private var userId: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//          val currentDate = Calendar.getInstance()
//       val formatter = SimpleDateFormat("dd-MM-YYYY")
//        val formatedDate = formatter.format(currentDate)

        database?.setPersistenceEnabled(true)
        database = FirebaseDatabase.getInstance()
        val referece = database!!.getReference("Data")
        val curentdate = referece.child("Demo")

        adapter = TodoAdapter(this, todoList as ArrayList<Todo>)
        rvTodos.adapter = adapter
        rvTodos.layoutManager = LinearLayoutManager(this)
        val callback = ItemTouchHelper(itemTouchClick)
        callback.attachToRecyclerView(rvTodos)

        btnAddtodo.setOnClickListener {
              showPopUp()
//            val title = editText.text.toString()
//            val amount = editText2.text.toString().toInt()
//            val todo = Todo(title, amount)
//            todoList.add(todo)
//            adapter.notifyDataSetChanged()
//            adapter.notifyItemInserted(todoList.size - 1)
//            rvTodos.scrollToPosition(todoList.size - 1)
//            editText.setText("")
//            hideKeyboard(editText)
        }

        btn_clear.setOnClickListener {
            todoList.clear()
            adapter.notifyDataSetChanged()
        }

        btn_send.setOnClickListener {
            for (todo in todoList) {
                curentdate.child(todo.name.toString()).setValue(todo.amount)
            }
        }
    }


    fun hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun showPopUp() {
        val view = layoutInflater.inflate(R.layout.userinfo_popup, null)
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add New Data")
        builder.setView(view)
        val dialog = builder.create()
        dialog.show()

        view.btn_add.setOnClickListener {
            val name = view.et_name.text.toString()
            val amount = view.et_amount.text.toString().toInt()
            val todo = Todo(name, amount)
            todoList.add(todo)
            adapter.notifyDataSetChanged()
            adapter.notifyItemInserted(todoList.size - 1)
            rvTodos.scrollToPosition(todoList.size - 1)
            dialog.dismiss()
        }
    }

}