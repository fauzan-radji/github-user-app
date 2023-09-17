package com.fauzan.githubuser.ui

import android.annotation.SuppressLint
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
import com.fauzan.githubuser.data.response.User
import com.fauzan.githubuser.databinding.FragmentHomeBinding
import com.fauzan.githubuser.utils.Error
import com.fauzan.githubuser.viewmodel.HomeViewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel>()
    private val users = arrayListOf<User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
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
        
        binding.rvUsers.adapter = UserAdapter(users) { user ->
            val toDetailFragment = HomeFragmentDirections.actionHomeFragmentToDetailFragment()
            toDetailFragment.username = user.login
            view?.findNavController()?.navigate(toDetailFragment)
        }
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

    @SuppressLint("NotifyDataSetChanged")
    private fun observe() {
        viewModel.searchQuery.observe(viewLifecycleOwner) { query ->
            binding.searchBar.text = query
            binding.searchBar.menu.findItem(R.id.action_clear).isVisible = query.isNotEmpty()
        }

        viewModel.users.observe(viewLifecycleOwner) { users ->
            this.users.clear()

            if(users == null) {
                binding.ivEmptyState.visibility = View.VISIBLE
            }else if (users.isEmpty()) {
                // TODO: Show not found image
                binding.ivEmptyState.visibility = View.VISIBLE
            } else {
                binding.ivEmptyState.visibility = View.GONE
                this.users.addAll(users)
            }

            binding.rvUsers.adapter?.notifyDataSetChanged()
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            Error(binding.root, errorMessage).show()
        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            if(isLoading) {
                binding.progressBar.visibility = View.VISIBLE
                binding.ivEmptyState.visibility = View.GONE
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