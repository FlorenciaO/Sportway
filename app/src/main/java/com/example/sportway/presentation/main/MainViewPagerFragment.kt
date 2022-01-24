package com.example.sportway.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.sportway.R
import com.example.sportway.databinding.FragmentMainViewPagerBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainViewPagerFragment: Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var binding: FragmentMainViewPagerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainViewPagerBinding.inflate(layoutInflater)

        viewPager = binding.viewPager.apply {
            adapter = MainViewPagerAdapter(childFragmentManager, lifecycle)
        }

        TabLayoutMediator(binding.tabs, viewPager) { tab, position ->
            val tabTitles = listOf(
                getString(R.string.events_title),
                getString(R.string.teams_title)
            )
            tab.text = tabTitles[position]
        }.attach()

        return binding.root
    }

}