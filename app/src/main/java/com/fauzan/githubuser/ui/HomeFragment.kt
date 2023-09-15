package com.fauzan.githubuser.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fauzan.githubuser.databinding.FragmentHomeBinding
import com.fauzan.githubuser.utils.Error

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            rvUsers.layoutManager = LinearLayoutManager(requireActivity())
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                val query = searchView.text.toString()
                binding.searchBar.text = query
                Log.d("HomeFragment", "Search: $query")
                binding.searchView.hide()
                viewModel.searchUsers(query)
                false
            }
        }

        viewModel.users.observe(viewLifecycleOwner) { users ->
            if(users == null) {
                binding.tvEmpty.visibility = View.VISIBLE
            } else {
                binding.tvEmpty.visibility = View.INVISIBLE
                binding.rvUsers.adapter = UserAdapter(users) { user ->
                    val toDetailFragment = HomeFragmentDirections.actionHomeFragmentToDetailFragment()
                    toDetailFragment.username = user.login
                    view.findNavController().navigate(toDetailFragment)
                }
            }
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Error(binding.root, it).show()
        }

        viewModel.loading.observe(viewLifecycleOwner) {isLoading ->
            if(isLoading) {
                binding.progressBar.visibility = View.VISIBLE
                binding.tvEmpty.visibility = View.INVISIBLE
            } else {
                binding.progressBar.visibility = View.INVISIBLE
                if(viewModel.users.value == null) {
                    binding.tvEmpty.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}