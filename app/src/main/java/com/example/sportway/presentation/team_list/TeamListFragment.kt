package com.example.sportway.presentation.team_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sportway.databinding.FragmentTeamListBinding
import com.example.sportway.presentation.MainActivity
import com.example.sportway.presentation.main.UiEventListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class TeamListFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = TeamListFragment()
    }

    private lateinit var uiEventListener: UiEventListener
    private val viewModel: TeamListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTeamListBinding.inflate(inflater, container, false)

        val teamListAdapter = TeamListAdapter()

        binding.teamList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = teamListAdapter
        }

        uiEventListener = activity as MainActivity

        lifecycleScope.launchWhenStarted {
            viewModel.teamListState.collectLatest {
                teamListAdapter.teamList = it.teams
                if (it.isLoading) uiEventListener.showLoading() else uiEventListener.finishLoading()
                it.error?.let { code -> uiEventListener.showError(code) }
            }
        }

        return binding.root
    }
}