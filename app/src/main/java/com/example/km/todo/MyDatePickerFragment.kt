package com.example.km.todo

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import java.text.SimpleDateFormat
import java.util.*

class MyDatePickerFragment : android.support.v4.app.DialogFragment(), DatePickerDialog.OnDateSetListener{

    var listener: onDatesetLisner? = null

    interface onDatesetLisner{
        fun onDateSelected(date : String)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(activity, this, year,month,day
        )
    }


    override fun onDateSet(datepicker: DatePicker?, year: Int, month: Int, day: Int) {

        val dateString = getDateString(year, month, day)

        //コールバックを呼ぶ
        listener?.onDateSelected(dateString)
        //フラグメントを閉じる
        fragmentManager.beginTransaction().remove(this).commit()

    }

    private fun getDateString(year: Int, month: Int, day: Int): String{

        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)

        return SimpleDateFormat("yyyy/MM/dd").format(calendar.time)

//        val dateFormat = SimpleDateFormat("yyyy/MM/dd")
//
//        return dateFormat.format(calendar.time)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is onDatesetLisner) {
            listener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }



}