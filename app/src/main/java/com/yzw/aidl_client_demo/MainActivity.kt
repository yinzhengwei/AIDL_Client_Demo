package com.yzw.aidl_client_demo

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.yzw.aidl_client_demo.Utils.hookOnClickListener
import com.yzw.aidl_service_demo.IMyAidlInterface
import com.yzw.aidl_service_demo.Person
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var iMyAidlInterface: IMyAidlInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindService()

        tbn_client.setOnClickListener {
            if (iMyAidlInterface != null) {
                iMyAidlInterface?.addPerson(Person("yzw"))

                val personList = iMyAidlInterface?.personList

                val text = StringBuffer("")
                personList?.forEach {
                    text.append(it.name)
                    text.append("\n")
                }
                Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
                Log.d("bindService", text.toString())
            }
        }
        hookOnClickListener(tbn_client)
    }

    private fun bindService() {
        val intent = Intent().apply {
            component = ComponentName(
                "com.yzw.aidl_service_demo",
                "com.yzw.aidl_service_demo.MyAidlService"
            )
        }
        bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    val connection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d("bindService", "onServiceConnected success")

            //获取连接的接口对象
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d("bindService", "onServiceDisconnected success")
            iMyAidlInterface = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(connection)
    }

}
