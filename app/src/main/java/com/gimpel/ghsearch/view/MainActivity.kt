package com.gimpel.ghsearch.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.gimpel.ghsearch.R
import com.gimpel.ghsearch.databinding.ActivityMainBinding
import com.gimpel.ghsearch.model.Repository
import com.gimpel.ghsearch.util.Status
import com.gimpel.ghsearch.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity(), RepositorySearchResultAdapter.OnItemClickListener {
    private val viewModel by viewModel<MainViewModel>()
    private lateinit var adapter: RepositorySearchResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpBinding()
        setUpUI()
        setUpObservers()
    }

    private fun setUpBinding() {
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main)
        binding.viewModel = viewModel
    }

    private fun setUpUI() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = RepositorySearchResultAdapter(arrayListOf(), this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter

        // when changing orientation
        viewModel.searchResult.value?.data.let {
            if (!it.isNullOrEmpty()) {
                refreshList(it)
            }
        }
    }

    private fun setUpObservers() {
        viewModel.searchQuery.observe(this, Observer { query ->
            if (query.isNullOrEmpty()) {
                refreshList(listOf())
            } else {
                viewModel.performGithubSearch(query)
            }
        })

        viewModel.searchResult.observe(this, Observer {
                when (it.status) {
                    Status.SUCCESS -> {
                        progressBar.visibility = View.GONE
                        it.data?.let { users -> refreshList(users) }
                        recyclerView.visibility = View.VISIBLE
                    }
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    }
                    Status.ERROR -> {
                        progressBar.visibility = View.GONE
                        Toast.makeText(this, "Error loading list", Toast.LENGTH_LONG).show()
                    }
                }
            })
    }

    override fun onItemClick(url: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    private fun refreshList(users: List<Repository>) {
        adapter.setData(users)
        adapter.notifyDataSetChanged()
    }
}




