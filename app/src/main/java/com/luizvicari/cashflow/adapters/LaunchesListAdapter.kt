package com.luizvicari.cashflow.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.luizvicari.cashflow.R
import com.luizvicari.cashflow.entities.Launch

class LaunchesListAdapter (val context: Context, private val dataset: List<Launch>): RecyclerView.Adapter<LaunchesListAdapter.ItemViewHolder>() {
    class ItemViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById<TextView>(R.id.tvListLaunch)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item: Launch = dataset [position]
        val launchString = "${item.type[0]} ${item.date} - ${item.details} - ${item.value}"
        holder.textView.text = launchString

    }
}