package com.fauzan.githubuser.ui.detail

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fauzan.githubuser.data.Result
import com.fauzan.githubuser.data.model.User
import com.fauzan.githubuser.databinding.FragmentFollowerBinding
import com.fauzan.githubuser.ui.UserAdapter
import com.fauzan.githubuser.ui.ViewModelFactory
import com.fauzan.githubuser.utils.Error

class FollowerFragment : Fragment() {

    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<DetailViewModel> { ViewModelFactory.getInstance(requireActivity()) }
    private val userAdapter: UserAdapter by lazy {
        UserAdapter(mutableListOf()) { user ->
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
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

        observe(username, type) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    userAdapter.updateData(result.data)
                }

                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Error(binding.root, result.message).show()
                }
            }
        }
    }

    private fun observe(username: String, type: FollowerType, observer: (Result<List<User>>) -> Unit) {
        when(type) {
            FollowerType.FOLLOWER -> {
                viewModel.getUserFollowers(username).observe(viewLifecycleOwner, observer)
            }
            FollowerType.FOLLOWING -> {
                viewModel.getUserFollowing(username).observe(viewLifecycleOwner, observer)
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