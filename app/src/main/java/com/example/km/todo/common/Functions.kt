package com.example.km.todo.common

import android.content.Context
import android.widget.Toast

/**
 * Created by koji_mitake on 2017/12/04.
 */

fun makeToast(context: Context, message:String){

    Toast.makeText(context,message,Toast.LENGTH_SHORT)

}