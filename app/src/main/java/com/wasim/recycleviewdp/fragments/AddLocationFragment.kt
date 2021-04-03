package com.wasim.demobottomnavigation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import com.wasim.recycleviewdp.R
import com.wasim.recycleviewdp.Todo
import kotlinx.android.synthetic.main.fragment_fetch_data.*

class AddLocationFragment : Fragment() {

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

                    snapshot.children.forEach {
                        val todo = it.getValue(Todo::class.java)

                        val name = todo?.name.toString()
                        val amount = todo?.amount.toString()
                        tv_fetchdata_fragment_output.append("$name $amount\n")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("error", error.toString())
                }

            })
        }
    }
}