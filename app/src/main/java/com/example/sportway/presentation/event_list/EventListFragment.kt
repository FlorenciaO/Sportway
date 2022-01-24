package com.example.sportway.presentation.event_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sportway.databinding.FragmentEventListBinding
import com.example.sportway.domain.model.Event
import com.example.sportway.presentation.MainActivity
import com.example.sportway.presentation.main.MainViewPagerFragmentDirections
import com.example.sportway.presentation.main.UiEventListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class EventListFragment: Fragment(), EventListAdapter.Listener {

    companion object {
        @JvmStatic
        fun newInstance() = EventListFragment()
    }

    private lateinit var binding: FragmentEventListBinding
    private lateinit var uiEventListener: UiEventListener
    private val viewModel: EventListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventListBinding.inflate(inflater, container, false)

        val eventListAdapter = EventListAdapter(this)

        binding.eventsList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = eventListAdapter
        }

        uiEventListener = activity as MainActivity
        lifecycleScope.launchWhenStarted {
            viewModel.eventListState.collectLatest {
                eventListAdapter.eventList = it.events

                if (it.isLoading) {
                    uiEventListener.showLoading()
                } else {
                    uiEventListener.finishLoading()
                }

                it.error?.let {
                    code -> uiEventListener.showError(code)
                }
            }
        }

        return binding.root
    }

    override fun onEventClicked(event: Event) {
        val action =
            MainViewPagerFragmentDirections.actionMainViewPagerFragmentToEventDetailsFragment(event)
        findNavController().navigate(action)
    }
}