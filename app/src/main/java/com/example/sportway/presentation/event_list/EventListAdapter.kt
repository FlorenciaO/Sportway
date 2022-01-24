package com.example.sportway.presentation.event_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.sportway.databinding.ViewEventOddItemBinding
import com.example.sportway.databinding.ViewEventPairItemBinding
import com.example.sportway.domain.model.Event

class EventListAdapter(private val eventListListener: Listener) : RecyclerView.Adapter<EventListAdapter.EventViewHolder>() {

    interface Listener {
        fun onEventClicked(event: Event)
    }

    var eventList: List<Event> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return if (viewType == 0) {
            EventViewHolder(ViewEventPairItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
            ))
        } else {
            EventViewHolder(ViewEventOddItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
            ))
        }
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = eventList[position]
        holder.itemView.setOnClickListener {
            eventListListener.onEventClicked(event)
        }
        holder.bind(event)
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    override fun getItemViewType(position: Int): Int {
        return (position + 1) % 2 // position starts from 0
    }

    class EventViewHolder(private val binding: ViewBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(event: Event) {
            when(binding) {
                is ViewEventPairItemBinding ->  {
                    binding.event = event
                    binding.executePendingBindings()
                }
                is ViewEventOddItemBinding -> {
                    binding.event = event
                    binding.executePendingBindings()
                }
            }
        }
    }
}