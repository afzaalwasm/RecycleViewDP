package com.wasim.recycleviewdp

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.wasim.demobottomnavigation.fragments.FetchDataFragment
import com.wasim.demobottomnavigation.fragments.FavoriteFragment
import com.wasim.demobottomnavigation.fragments.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
//    lateinit var adapter: TodoAdapter
//    val todoList = arrayListOf<Todo>(
//
////        Todo("Follow androidDevs", false),
////        Todo("Follow androidDevs", true),
////        Todo("Follow androidDevs", false),
////        Todo("Follow androidDevs", false),
////        Todo("Follow androidDevs", true),
////        Todo("Follow androidDevs", true)
//    )
//    this function is use to delete item using swiped
//    private val itemTouchClick = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
//        override fun onMove(
//            recyclerView: RecyclerView,
//            viewHolder: RecyclerView.ViewHolder,
//            target: RecyclerView.ViewHolder
//        ): Boolean = false
//
//        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//            (todoList as ArrayList<Todo>).removeAt(viewHolder.adapterPosition)
//            adapter.notifyDataSetChanged()
//
//            //adapter.notifyItemChanged(viewHolder.adapterPosition)
//        }
//    }
//    this is database referance
//    var database: FirebaseDatabase? = null
//    private var userId: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val homeFragment = HomeFragment()
        val favoriteFragment = FavoriteFragment()
        val addLocationFragment = FetchDataFragment()

        makeCurrentFragment(homeFragment)
        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_home -> makeCurrentFragment(homeFragment)
                R.id.add_locati0n -> makeCurrentFragment(addLocationFragment)
                R.id.favorite -> makeCurrentFragment(favoriteFragment)
            }
            true
        }


//          val currentDate = Calendar.getInstance()
//       val formatter = SimpleDateFormat("dd-MM-YYYY")
//        val formatedDate = formatter.format(currentDate)


//        call database
//        database?.setPersistenceEnabled(true)
//        database = FirebaseDatabase.getInstance()
//        val referece = database!!.getReference("Data")
//        val curentdate = referece.child("Demo")
//
//        adapter = TodoAdapter(this, todoList as ArrayList<Todo>)
//        rvTodos.adapter = adapter
//        rvTodos.layoutManager = LinearLayoutManager(this)
//        val callback = ItemTouchHelper(itemTouchClick)
//        callback.attachToRecyclerView(rvTodos)

//        btnAddtodo.setOnClickListener {
//              showPopUp()
////            val title = editText.text.toString()
////            val amount = editText2.text.toString().toInt()
////            val todo = Todo(title, amount)
////            todoList.add(todo)
////            adapter.notifyDataSetChanged()
////            adapter.notifyItemInserted(todoList.size - 1)
////            rvTodos.scrollToPosition(todoList.size - 1)
////            editText.setText("")
////            hideKeyboard(editText)
//        }

//        btn_clear.setOnClickListener {
//            todoList.clear()
//            adapter.notifyDataSetChanged()
//        }

//
//        btn_send.setOnClickListener {
//            for (todo in todoList) {
//                curentdate.child(todo.name.toString()).setValue(todo.amount)
//            }
//        }
    }
    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_fragment_hostage, fragment)
            commit()
        }

    fun hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

//    fun showPopUp() {
//        val view = layoutInflater.inflate(R.layout.userinfo_popup, null)
//        val builder = AlertDialog.Builder(this)
////        builder.setTitle("Add New Data")
//        builder.setView(view)
//        val dialog = builder.create()
//        dialog.show()
//        view.iv_close.setOnClickListener{
//            dialog.dismiss()
//        }
//
//
//        view.btn_add.setOnClickListener {
//            val name = view.et_name.text.toString()
//            val amount = view.et_amount.text.toString().toInt()
//            val todo = Todo(name, amount)
//            todoList.add(todo)
//            adapter.notifyDataSetChanged()
//            adapter.notifyItemInserted(todoList.size - 1)
//            rvTodos.scrollToPosition(todoList.size - 1)
//            dialog.dismiss()
//        }
//    }


//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        return super.onCreateOptionsMenu(menu)
//    }

}