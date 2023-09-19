package com.fauzan.githubuser.ui

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
import com.fauzan.githubuser.databinding.FragmentHomeBinding
import com.fauzan.githubuser.utils.Error
import com.fauzan.githubuser.viewmodel.HomeViewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel>()
    private val userAdapter: UserAdapter by lazy {
        UserAdapter(mutableListOf()) { user ->
            val toDetailFragment = HomeFragmentDirections.actionHomeFragmentToDetailFragment()
            toDetailFragment.username = user.login
            view?.findNavController()?.navigate(toDetailFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
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

    private fun observe() {
        viewModel.users.observe(viewLifecycleOwner) { users ->
            if(users == null) {
                showEmptyState(true)
                showNoData(false)
            } else if (users.isEmpty()) {
                showNoData(true)
                showEmptyState(false)
            } else {
                showEmptyState(false)
                showNoData(false)
            }

            userAdapter.updateData(users)
        }

        viewModel.searchQuery.observe(viewLifecycleOwner) { query ->
            binding.searchBar.text = query
            binding.searchBar.menu.findItem(R.id.action_clear).isVisible = query.isNotEmpty()
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            Error(binding.root, errorMessage).show()
        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            if(isLoading) {
                binding.progressBar.visibility = View.VISIBLE

                showEmptyState(false)
                showNoData(false)

                binding.rvUsers.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.GONE
                binding.rvUsers.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}