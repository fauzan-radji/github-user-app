package com.fauzan.githubuser.ui.favorite

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.fauzan.githubuser.data.Result
import com.fauzan.githubuser.databinding.FragmentFavoriteBinding
import com.fauzan.githubuser.ui.UserAdapter
import com.fauzan.githubuser.ui.ViewModelFactory
import com.fauzan.githubuser.utils.Error

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<FavoriteViewModel> { ViewModelFactory.getInstance(requireActivity()) }
    private val userAdapter: UserAdapter by lazy {
        UserAdapter(mutableListOf()) { user ->
            val toDetailFragment = FavoriteFragmentDirections.actionNavigationFavoriteToNavigationDetail()
            toDetailFragment.username = user.login
            view?.findNavController()?.navigate(toDetailFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewModel.getFavoriteUsers().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE

                    showEmptyState(false)
                }

                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE

                    val users = result.data
                    if (users.isNotEmpty()) {
                        userAdapter.updateData(users)
                        binding.rvFavorites.visibility = View.VISIBLE
                        showEmptyState(false)
                    } else {
                        binding.rvFavorites.visibility = View.GONE
                        showEmptyState(true)
                    }
                }

                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvFavorites.visibility = View.GONE
                    showEmptyState(true)

                    Error(binding.root, result.message).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showEmptyState(state: Boolean) {
        if (state) {
            binding.ivEmptyState.visibility = View.VISIBLE
            binding.tvEmptyState.visibility = View.VISIBLE
        } else {
            binding.ivEmptyState.visibility = View.GONE
            binding.tvEmptyState.visibility = View.GONE
        }
    }

    private fun setupRecyclerView() {
        val orientation = activity?.resources?.configuration?.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding.rvFavorites.layoutManager = LinearLayoutManager(activity)
        } else if(orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvFavorites.layoutManager = GridLayoutManager(activity, 2)
        }

        binding.rvFavorites.adapter = userAdapter
    }
}