package com.example.sportway.presentation.event_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sportway.databinding.ViewStatItemBinding
import com.example.sportway.domain.model.Stat

class StatListAdapter: RecyclerView.Adapter<StatListAdapter.StatViewHolder>() {

    var statList: List<Stat> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatViewHolder {
        return StatViewHolder(ViewStatItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: StatViewHolder, position: Int) {
        holder.bind(statList[position])
    }

    override fun getItemCount(): Int {
        return statList.size
    }

    class StatViewHolder(private val binding: ViewStatItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(stat: Stat) {
            binding.stat = stat
            binding.executePendingBindings()
        }
    }
}