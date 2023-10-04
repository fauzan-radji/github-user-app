package com.fauzan.githubuser.ui.home

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
import com.fauzan.githubuser.R
import com.fauzan.githubuser.data.Result
import com.fauzan.githubuser.databinding.FragmentHomeBinding
import com.fauzan.githubuser.ui.UserAdapter
import com.fauzan.githubuser.ui.ViewModelFactory
import com.fauzan.githubuser.utils.Error

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel> { ViewModelFactory.getInstance(requireActivity()) }
    private val userAdapter: UserAdapter by lazy {
        UserAdapter(mutableListOf()) { user ->
            val toDetailFragment = HomeFragmentDirections.actionHomeFragmentToDetailFragment()
            toDetailFragment.username = user.login
            view?.findNavController()?.navigate(toDetailFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        observe()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSearchBar()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observe() {
        viewModel.users.observe(viewLifecycleOwner) { result ->
            when(result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rvUsers.visibility = View.GONE
                    showEmptyState(false)
                    showNoData(false)
                }

                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE

                    val users = result.data
                    if(users.isEmpty()) {
                        if(binding.searchView.text.toString().isEmpty()) {
                            showEmptyState(true)
                            showNoData(false)
                        } else {
                            showEmptyState(false)
                            showNoData(true)
                        }
                    } else {
                        showEmptyState(false)
                        showNoData(false)
                    }

                    userAdapter.updateData(users)
                    binding.rvUsers.visibility = View.VISIBLE
                }

                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvUsers.visibility = View.GONE
                    showEmptyState(false)
                    showNoData(true)
                    Error(binding.root, result.message).show()
                }
            }
        }

        viewModel.searchQuery.observe(viewLifecycleOwner) { query ->
            binding.searchBar.text = query
            binding.searchBar.menu.findItem(R.id.action_clear).isVisible = query.isNotEmpty()
        }
    }

    private fun showEmptyState(show: Boolean) {
        if(show) {
            binding.ivEmptyState.visibility = View.VISIBLE
            binding.tvEmptyState.visibility = View.VISIBLE
        } else {
            binding.ivEmptyState.visibility = View.GONE
            binding.tvEmptyState.visibility = View.GONE
        }
    }

    private fun showNoData(show: Boolean) {
        if(show) {
            binding.ivNoData.visibility = View.VISIBLE
            binding.tvNoData.visibility = View.VISIBLE
        } else {
            binding.ivNoData.visibility = View.GONE
            binding.tvNoData.visibility = View.GONE
        }
    }

    private fun setupRecyclerView() {
        val orientation = activity?.resources?.configuration?.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding.rvUsers.layoutManager = LinearLayoutManager(activity)
        } else if(orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvUsers.layoutManager = GridLayoutManager(activity, 2)
        }

        binding.rvUsers.adapter = userAdapter
    }

    private fun setupSearchBar() {
        binding.searchView.setupWithSearchBar(binding.searchBar)
        binding.searchView.editText.setOnEditorActionListener { _, _, _ ->
            viewModel.searchUsers(binding.searchView.text.toString())
            binding.searchView.hide()
            false
        }

        binding.searchBar.setOnMenuItemClickListener {
            viewModel.searchUsers("")
            false
        }
    }
}