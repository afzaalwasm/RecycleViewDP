package com.wasim.demobottomnavigation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import com.wasim.recycleviewdp.R
import com.wasim.recycleviewdp.Todo
import com.wasim.recycleviewdp.TodoAdapter
import kotlinx.android.synthetic.main.fragment_fetch_data.*
import kotlinx.android.synthetic.main.fragment_home.*

class FetchDataFragment : Fragment() {
    val todoList = arrayListOf<Todo>()
    lateinit var todoAdapter: TodoAdapter
    var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val referece = database!!.getReference("Data")
    val curentdate = referece.child("03-04-2021")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fetch_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_fetch.setOnClickListener {
            curentdate.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    todoList.clear()
                    snapshot.children.forEach {
                        val todo = it.getValue(Todo::class.java)
                        todoList.add(todo!!)
                        val name = todo?.name.toString()
                        val amount = todo?.amount.toString()


//                        tv_fetchdata_fragment_output.append("$name\n")
//                        tv_fetchdata_fragment_output2.append("$amount\n")
                    }
                    todoAdapter = TodoAdapter(HomeFragment.ctx!!, todoList as ArrayList<Todo>)
                    if (isAdded) {
                        rv_fetch_fragment.adapter = todoAdapter
                        rv_fetch_fragment.layoutManager = LinearLayoutManager(HomeFragment.ctx!!)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("error", error.toString())
                }

            })
        }
    }
}