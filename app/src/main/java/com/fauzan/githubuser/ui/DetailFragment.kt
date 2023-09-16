package com.fauzan.githubuser.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.fauzan.githubuser.R
import com.fauzan.githubuser.databinding.FragmentDetailBinding
import com.fauzan.githubuser.viewmodel.DetailViewModel
import com.google.android.material.tabs.TabLayoutMediator

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        binding.topAppBar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val safeArgs = DetailFragmentArgs.fromBundle(arguments as Bundle)
        viewModel.getUserDetail(safeArgs.username)
        observe()

        val sectionsPagerAdapter = SectionsPagerAdapter(activity as AppCompatActivity, safeArgs.username)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when(position) {
                0 -> tab.text = getString(R.string.followers)
                1 -> tab.text = getString(R.string.following)
            }
        }.attach()
    }

    private fun observe() {
        viewModel.user.observe(viewLifecycleOwner) { user ->
            binding.tvName.text = user.name
            binding.tvUsername.text = user.login
            Glide.with(this)
                .load(user.avatarUrl)
                .into(binding.ivProfile)

            binding.tvRepositories.text = user.publicRepos.toString()
            binding.tvGists.text = user.publicGists.toString()

            binding.tabLayout.getTabAt(0)?.text = "${getString(R.string.followers)} (${user.followers})"
            binding.tabLayout.getTabAt(1)?.text = "${getString(R.string.following)} (${user.following})"
        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            if(isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}