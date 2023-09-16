package com.fauzan.githubuser.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
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

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            rvUsers.layoutManager = LinearLayoutManager(activity)
            rvUsers.adapter = UserAdapter(users) { user ->
                val toDetailFragment = HomeFragmentDirections.actionHomeFragmentToDetailFragment()
                toDetailFragment.username = user.login
                view.findNavController().navigate(toDetailFragment)
            }
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                val query = searchView.text.toString()
                onQueryChange(query)
                searchView.hide()
                false
            }

            searchBar.setOnMenuItemClickListener {
                onQueryChange("")
                false
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onQueryChange(query: String) {
        binding.searchBar.text = query
        binding.searchBar.menu.findItem(R.id.action_clear).isVisible = query.isNotEmpty()
        if(query.isEmpty()) {
            this.users.clear()
            binding.rvUsers.adapter?.notifyDataSetChanged()
            binding.ivEmptyState.visibility = View.VISIBLE
        } else {
            viewModel.searchUsers(query)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observe() {
        viewModel.users.observe(viewLifecycleOwner) { users ->
            if(users == null) {
                binding.ivEmptyState.visibility = View.VISIBLE
            } else {
                binding.ivEmptyState.visibility = View.GONE
                this.users.clear()
                this.users.addAll(users)
                binding.rvUsers.adapter?.notifyDataSetChanged()
            }
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Error(binding.root, it).show()
        }

        viewModel.loading.observe(viewLifecycleOwner) {isLoading ->
            if(isLoading) {
                binding.progressBar.visibility = View.VISIBLE
                binding.ivEmptyState.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.GONE
                if(viewModel.users.value == null) {
                    binding.ivEmptyState.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}