package com.example.sportway.presentation.event_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sportway.R
import com.example.sportway.databinding.FragmentEventDetailsBinding
import com.example.sportway.presentation.MainActivity
import com.example.sportway.presentation.main.UiEventListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class EventDetailsFragment: Fragment() {

    private lateinit var binding: FragmentEventDetailsBinding
    private lateinit var uiEventListener: UiEventListener
    private val viewModel: EventDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventDetailsBinding.inflate(inflater, container, false)

        uiEventListener = activity as MainActivity
        val statListAdapter = StatListAdapter()

        binding.statList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = statListAdapter
        }

        lifecycleScope.launchWhenStarted {
            viewModel.eventDetailsState.collectLatest {
                it.event?.apply {
                    statListAdapter.statList = details?.stats ?: emptyList()
                    binding.tournamentText.text = details?.tournament
                    binding.teamsNameText.text = getString(R.string.vs_teams, homeTeam, awayTeam)
                    binding.scoreText.text = getString(R.string.score_teams, homeScore, awayScore)
                }
                if (it.isLoading) {
                    uiEventListener.showLoading()
                    binding.statsTitleText.visibility = View.INVISIBLE
                } else {
                    uiEventListener.finishLoading()
                    binding.statsTitleText.visibility = View.VISIBLE
                }
                it.error?.let { code -> uiEventListener.showError(code) }
            }
        }

        return binding.root
    }

}