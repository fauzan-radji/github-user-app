package com.fauzan.githubuser.ui

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fauzan.githubuser.data.response.User
import com.fauzan.githubuser.databinding.FragmentFollowerBinding
import com.fauzan.githubuser.utils.Error
import com.fauzan.githubuser.viewmodel.DetailViewModel

class FollowerFragment : Fragment() {

    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<DetailViewModel>()
    private val users = arrayListOf<User>()
    private val userAdapter: UserAdapter by lazy {
        UserAdapter(users) { user ->
            val toDetailFragment = DetailFragmentDirections.actionDetailFragmentSelf()
            toDetailFragment.username = user.login
            view?.findNavController()?.navigate(toDetailFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)
        observe()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvUsers.layoutManager = LinearLayoutManager(activity)
        binding.rvUsers.adapter = userAdapter

        val username = arguments?.getString(EXTRA_USERNAME) as String
        val type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getSerializable(EXTRA_TYPE, FollowerType.FOLLOWER::class.java) as FollowerType
        } else {
            @Suppress("DEPRECATION")
            arguments?.getSerializable(EXTRA_TYPE) as FollowerType
        }

        when(type) {
            FollowerType.FOLLOWER -> {
                viewModel.getUserFollowers(username)
            }
            FollowerType.FOLLOWING -> {
                viewModel.getUserFollowing(username)
            }
        }
    }

    private fun observe() {
        viewModel.users.observe(viewLifecycleOwner) { users ->
            userAdapter.updateData(users)
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Error(binding.root, it).show()
        }

        viewModel.loading.observe(viewLifecycleOwner) {isLoading ->
            if(isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.INVISIBLE
            }
        }
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_TYPE = "extra_type"
    }
    enum class FollowerType {
        FOLLOWER, FOLLOWING
    }
}