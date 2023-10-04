package com.fauzan.githubuser.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.fauzan.githubuser.R
import com.fauzan.githubuser.data.Result
import com.fauzan.githubuser.data.model.User
import com.fauzan.githubuser.databinding.FragmentDetailBinding
import com.fauzan.githubuser.ui.ViewModelFactory
import com.fauzan.githubuser.utils.Error
import com.google.android.material.tabs.TabLayoutMediator

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<DetailViewModel> { ViewModelFactory.getInstance(requireActivity()) }
    private var user: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val safeArgs = DetailFragmentArgs.fromBundle(arguments as Bundle)
        observe(safeArgs.username)

        val sectionsPagerAdapter = SectionsPagerAdapter(activity as AppCompatActivity, safeArgs.username)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when(position) {
                0 -> tab.text = getString(R.string.followers)
                1 -> tab.text = getString(R.string.following)
            }
        }.attach()

        binding.fabFavorite?.setOnClickListener {
            user?.let { user ->
                if (user.isFavorite) viewModel.removeFavorite(user)
                else viewModel.addFavorite(user)
            }
        }
    }

    private fun observe(username: String) {
        viewModel.getUserDetail(username).observe(viewLifecycleOwner) { result ->
            when(result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE

                    user = result.data
                    user?.let { user ->
                        binding.tvName.text = user.name
                        binding.tvUsername.text = user.login
                        Glide.with(this)
                            .load(user.avatarUrl)
                            .placeholder(R.drawable.default_avatar)
                            .into(binding.ivProfile)

                        binding.tvRepositories.text = user.publicRepos.toString()
                        binding.tvGists.text = user.publicGists.toString()

                        binding.tabLayout.getTabAt(0)?.text = "${getString(R.string.followers)} (${user.followers})"
                        binding.tabLayout.getTabAt(1)?.text = "${getString(R.string.following)} (${user.following})"

                        binding.fabFavorite?.setImageResource(
                            if (user.isFavorite) R.drawable.ic_favorite
                            else R.drawable.ic_favorite_border
                        )
                    }
                }

                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Error(binding.root, result.message).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}