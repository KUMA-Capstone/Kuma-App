package com.capstone.kuma.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.kuma.R
import com.capstone.kuma.data.TipsItem

class ListTipsAdapter(private val listTips: ArrayList<TipsItem>) : RecyclerView.Adapter<ListTipsAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.list_tips, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (img, title) = listTips[position]
        holder.imgPhoto.setImageResource(img)
        holder.tvTitle.text = title
        holder.itemView.setOnClickListener {

        }
    }

    override fun getItemCount(): Int = listTips.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.TipsImage)
        val tvTitle: TextView = itemView.findViewById(R.id.TipsTitle)
    }
}