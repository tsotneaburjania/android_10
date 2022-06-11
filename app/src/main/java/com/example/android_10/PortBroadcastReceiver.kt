package com.example.android_10

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.android_10.data.EvtRecord
import com.example.android_10.data.SQLiteHandler


class PortBroadcastReceiver  : BroadcastReceiver() {

    @Override
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_HEADSET_PLUG) {
            when (intent.getIntExtra("state", -1)) {
                0 -> Toast.makeText(context, "HEADPHONES DISCONNECTED", Toast.LENGTH_SHORT).show()
                1 -> Toast.makeText(context, "HEADPHONES CONNECTED", Toast.LENGTH_SHORT).show()
            }

        }
        if (intent.action == Intent.ACTION_POWER_CONNECTED) {
            Toast.makeText(context, "POWER CONNECTED", Toast.LENGTH_SHORT).show()

        }
        if (intent.action == Intent.ACTION_POWER_DISCONNECTED) {
            Toast.makeText(context, "POWER DISCONNECTED", Toast.LENGTH_SHORT).show()
        }

        if (intent.action == Intent.ACTION_POWER_CONNECTED || intent.action == Intent.ACTION_POWER_DISCONNECTED){
            val myIntent = Intent()
            myIntent.action = "ACTION_REFRESH_UI"

            context.sendBroadcast(myIntent)
        }

        if (intent.action == Intent.ACTION_HEADSET_PLUG || intent.action == Intent.ACTION_POWER_CONNECTED || intent.action == Intent.ACTION_POWER_DISCONNECTED){
            val sqLiteHandler = SQLiteHandler(context)
            val status = sqLiteHandler.addEvtRecord(EvtRecord((0..1453).random(),
                intent.getIntExtra("state", -1), java.sql.Timestamp(System.currentTimeMillis()).toString(), intent.action.toString())
            )
            if(status > -1){
                Toast.makeText(context,"EVENT RECORD SAVED",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context,"EVENT RECORD FAILED",Toast.LENGTH_SHORT).show()
            }
        }





}

}