package com.example.android_10

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_10.adapters.RecordListAdapter
import com.example.android_10.data.SQLiteHandler


class MainActivity : AppCompatActivity() {

    private val reloadReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            finish()
            startActivity(getIntent())
        }
    }
    lateinit var pReceiver: PortBroadcastReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val filter = IntentFilter("ACTION_REFRESH_UI")
        registerReceiver(reloadReceiver, filter)
        init()
    }

    override fun onResume() {
        super.onResume()
        pReceiver = PortBroadcastReceiver()
        val filter = IntentFilter(Intent.ACTION_HEADSET_PLUG)
        filter.addAction(Intent.ACTION_POWER_CONNECTED)
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED)
        registerReceiver(pReceiver, filter)
    }

    override fun onPause() {
        super.onPause()
        if (pReceiver != null) {
            unregisterReceiver(pReceiver)
        }

    }

    private fun init(){
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        val sqLiteHandler = SQLiteHandler(this)
        println(sqLiteHandler.getAllEvtRecords())
        recyclerView.adapter = RecordListAdapter(sqLiteHandler.getAllEvtRecords())
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
    }
}