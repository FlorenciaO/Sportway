package com.example.sportway.presentation.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.sportway.common.Constants.MAIN_NUM_PAGES
import com.example.sportway.presentation.event_list.EventListFragment
import com.example.sportway.presentation.team_list.TeamListFragment

class MainViewPagerAdapter(f: FragmentManager, l: Lifecycle) : FragmentStateAdapter(f, l) {
    override fun getItemCount(): Int = MAIN_NUM_PAGES

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) EventListFragment.newInstance()
        else TeamListFragment.newInstance()
    }
}