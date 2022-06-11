package com.example.android_10.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android_10.R
import com.example.android_10.data.EvtRecord

class RecordListAdapter(private val list: List<EvtRecord>) : RecyclerView.Adapter<RecordListAdapter.ResourceViewHolder>() {
    class ResourceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private var idTv: TextView = itemView.findViewById(R.id.idTv)
        private var stateTv: TextView = itemView.findViewById(R.id.stateTv)
        private var actionTimeTv: TextView = itemView.findViewById(R.id.actionTimeTv)
        private var actionTypeTv: TextView = itemView.findViewById(R.id.actionTypeTv)

        private lateinit var record: EvtRecord

        @SuppressLint("SetTextI18n")
        fun onBind(record: EvtRecord){
            this.record = record

            idTv.text = "ID: " + record.id.toString()
            stateTv.text = "STATE: " + record.state.toString()
            actionTimeTv.text = "ACTION TIME: " + record.actionTime
            actionTypeTv.text = "ACTION TYPE: " + record.actionType
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResourceViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_record, parent, false)

        return ResourceViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ResourceViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size
}