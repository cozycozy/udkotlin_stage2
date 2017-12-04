package com.example.km.todo

import io.realm.RealmObject

/**
 * Created by koji_mitake on 2017/12/04.
 */
open class TodoModel : RealmObject() {

    //タイトル
    var title : String = ""

    //期日
    var deadLine : String = ""

    //内容
    var taskDetail : String = ""

    //完了フラグ
    var isCompleted : Boolean = false

}