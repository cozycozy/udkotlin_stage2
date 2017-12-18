package com.example.km.todo

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.widget.EditText
import com.example.km.todo.common.FragmentTag
import com.example.km.todo.common.IntentKey
import com.example.km.todo.common.ModeInEit
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity(), EditFragment.OnFragmentInteractionListener,MyDatePickerFragment.onDatesetLisner {

    //EditFragmentからのCallback
    override fun onDataEdited() {
        finish()
    }

    //DateFragmentからのCallBack
    override fun onDateSelected(dateString: String) {

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val inputDateText = findViewById<EditText>(R.id.inputDateText) as EditText

        //日付の設定
        inputDateText.setText(dateString)

    }

    override fun onDatePickerLaunched() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        MyDatePickerFragment().show(supportFragmentManager, FragmentTag.DATE_PICKER.toString())
    }

    override fun onFragmentInteraction(uri: Uri) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setSupportActionBar(toolbar)

        //戻るアイコンの設定
        toolbar.apply {
            setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
            setNavigationOnClickListener {
                finish()
            }
        }

        //インテント取得
        val bundle = intent.extras
        val title = bundle.getString(IntentKey.TITLE.name)
        val deadline = bundle.getString(IntentKey.DEADLINE.name)
        val taskDetal = bundle.getString(IntentKey.TASK_DETAIL.name)
        val isComplete = bundle.getBoolean(IntentKey.IS_COMPLETED.name)
        val mode = bundle.getSerializable(IntentKey.MODE_IN_EDIT.name) as ModeInEit

        //EditFragmentを開く
        supportFragmentManager.beginTransaction()
                .add(R.id.container_detail,EditFragment.newInstance(title,deadline,taskDetal,isComplete,mode),FragmentTag.EDIT.toString())
                .commit()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_main, menu)
        menu?.apply {
            findItem(R.id.menu_delete).isVisible = false
            findItem(R.id.menu_edit).isVisible = false
            findItem(R.id.menu_resister).isVisible = false

        }

        return true
    }

}
