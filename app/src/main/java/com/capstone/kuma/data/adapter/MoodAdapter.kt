package com.capstone.kuma.data.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.capstone.kuma.api.moodResult
import com.capstone.kuma.databinding.ListMoodBinding
import com.capstone.kuma.layout.ui.history.HistoryDetailActivity

class MoodAdapter: PagingDataAdapter<moodResult, MoodAdapter.ListViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val bindingLayer = ListMoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(bindingLayer)
    }

    override fun onBindViewHolder(holder: MoodAdapter.ListViewHolder, position: Int) {
        val data = getItem(position)
        if(data != null){
            holder.bind(data)
        }
    }

    inner class ListViewHolder(private val binding: ListMoodBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(data: moodResult){
            binding.date.text = data.date
            binding.subMood.text = data.sub_mood

            itemView.setOnClickListener{
                val intent = Intent(itemView.context, HistoryDetailActivity::class.java)
                intent.putExtra(HistoryDetailActivity.OBJECT, data)
                itemView.context.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(itemView.context as Activity).toBundle())
            }
        }
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<moodResult>(){
            override fun areItemsTheSame(oldItem: moodResult, newItem: moodResult): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: moodResult, newItem: moodResult): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }
}