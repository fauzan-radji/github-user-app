package com.fauzan.githubuser.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity, private val username: String) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowerFragment()
        fragment.arguments = Bundle().apply {
            putString(FollowerFragment.EXTRA_USERNAME, username)
            when(position) {
                0 -> putSerializable(FollowerFragment.EXTRA_TYPE, FollowerFragment.FollowerType.FOLLOWER)
                1 -> putSerializable(FollowerFragment.EXTRA_TYPE, FollowerFragment.FollowerType.FOLLOWING)
            }
        }
        return fragment
    }
}