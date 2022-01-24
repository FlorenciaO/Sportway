package com.example.sportway.presentation.team_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sportway.databinding.ViewTeamItemBinding
import com.example.sportway.domain.model.Team

class TeamListAdapter: RecyclerView.Adapter<TeamListAdapter.TeamViewHolder>() {

    var teamList: List<Team> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder(ViewTeamItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bind(teamList[position])
    }

    override fun getItemCount(): Int {
        return teamList.size
    }

    class TeamViewHolder(private val binding: ViewTeamItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(team: Team) {
            binding.team = team
            binding.executePendingBindings()
        }
    }
}