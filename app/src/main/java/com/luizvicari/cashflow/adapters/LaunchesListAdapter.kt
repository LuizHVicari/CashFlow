package com.luizvicari.cashflow.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.luizvicari.cashflow.R
import com.luizvicari.cashflow.entities.Launch

val creditStrings = arrayOf(2,"Crédito", "Credit")

class LaunchesListAdapter (val context: Context, private val dataset: List<Launch>): RecyclerView.Adapter<LaunchesListAdapter.ItemViewHolder>() {
    class ItemViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById<TextView>(R.id.tvListLaunch)
        val dateView: TextView = view.findViewById<TextView>(R.id.tvListDate)
        val cardView: CardView = view.findViewById<CardView>(R.id.cardView)
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
        val launchString = "${item.type[0]} | ${item.details} | ${item.value}"
        holder.textView.text = launchString
        holder.dateView.text = item.date

        if (item.type in creditStrings) {
            holder.cardView.setBackgroundColor(Color.parseColor("#B8EEAA"))
        } else {
            holder.cardView.setBackgroundColor(Color.parseColor("#FEE5D7"))

        }

    }
}