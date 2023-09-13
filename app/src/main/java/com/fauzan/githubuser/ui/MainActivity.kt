package com.fauzan.githubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.fauzan.githubuser.databinding.ActivityMainBinding
import com.fauzan.githubuser.utils.Error as ErrorUtil

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            rvUsers.layoutManager = LinearLayoutManager(this@MainActivity)
            searchView.editText.setOnEditorActionListener { _, _, _ ->
                val query = searchView.text.toString()
                searchBar.text = query
                searchView.hide()
                viewModel.searchUsers(query)
                false
            }
        }

        viewModel.users.observe(this) {users ->
            if(users == null) {
                TODO("Empty state")
            } else {
                binding.rvUsers.adapter = UserAdapter(users)
            }
        }

        viewModel.error.observe(this) {
            ErrorUtil(binding.root, it)
        }
    }
}