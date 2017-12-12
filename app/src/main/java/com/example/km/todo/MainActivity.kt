package com.example.km.todo

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.km.todo.common.FragmentTag
import com.example.km.todo.common.IntentKey.*
import com.example.km.todo.common.ModeInEit
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var isTwoPain : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        if(findViewById<View>(R.id.container_detail) != null){
            isTwoPain = true
        }

        fab.setOnClickListener { view ->
            goEditScreen()

            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()



        }
    }

    private fun goEditScreen() {

        if(isTwoPain == true) {


            return
        }

        val intent = Intent(this@MainActivity, EditActivity::class.java).apply{

            putExtra("","","",false,ModeInEit.EDIT)

        }


    }

    private fun putExtra(title : String, deadline : String, taskDetail : String, isCompleted : Boolean,
                         mode : ModeInEit) {

        if(isTwoPain == true){
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.container_detail, EditFragment.newInstance(title,deadline,taskDetail,isCompleted,mode), FragmentTag.EDIT.toString())
                    .commit()
//            val fragmentManager = supportFragmentManager
//            val fragmentTransaction = fragmentManager.beginTransaction()
//            fragmentTransaction.add(R.id.container_detail, EditFragment.newInstance("1","1"))
//            fragmentTransaction.commit()
            return

        }
        val intent = Intent(this@MainActivity, EditActivity::class.java).apply {
            putExtra(TITLE.name, title)
            putExtra(DEADLINE.name, deadline)
            putExtra(TASK_DETAIL.name, taskDetail)
            putExtra(IS_COMPLETED.name, isCompleted)
            putExtra(MODE_IN_EDIT.name, mode)
        }
        startActivity(intent)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        menu.apply {
            findItem(R.id.menu_delete).isVisible = false
            findItem(R.id.menu_edit).isVisible = false
            findItem(R.id.menu_resister).isVisible = false
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
