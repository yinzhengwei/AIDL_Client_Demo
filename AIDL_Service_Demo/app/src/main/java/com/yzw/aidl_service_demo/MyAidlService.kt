package com.yzw.aidl_service_demo

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

/**
 * Create by yinzhengwei on 2019-10-10
 * @Function
 */
class MyAidlService : Service() {

    private var persons = mutableListOf<Person>()

    override fun onBind(intent: Intent?): IBinder? {
        val iBinder = object : IMyAidlInterface.Stub() {
            override fun addPerson(person: Person) {

                person.name = "我来自服务端：${person.name}"

                persons.add(person)
            }

            override fun getPersonList() = persons
        }
        return iBinder
    }

    override fun onCreate() {
        super.onCreate()

        Log.d("bindService", "onCreate success")
    }

}