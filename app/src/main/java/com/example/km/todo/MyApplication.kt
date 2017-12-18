package com.example.km.todo

import android.app.Application
import android.content.Context
import io.realm.Realm

/**
 * Created by koji_mitake on 2017/12/04.
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

        //アプリケーションレベルでのContext
        appContext = this
    }

    companion object {
        lateinit var appContext: Context
    }
}