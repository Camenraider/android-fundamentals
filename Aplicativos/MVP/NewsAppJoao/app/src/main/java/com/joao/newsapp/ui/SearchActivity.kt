package com.joao.newsapp.ui

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joao.newsapp.adapter.MainAdapter
import com.joao.newsapp.databinding.ActivitySearchBinding
import com.joao.newsapp.model.Article
import com.joao.newsapp.model.data.NewsDataSource
import com.joao.newsapp.presenter.ViewHome
import com.joao.newsapp.presenter.search.SearchPresenter
import com.joao.newsapp.util.UtilQueryTextListener

class SearchActivity : AppCompatActivity(), ViewHome.View {

    private lateinit var binding: ActivitySearchBinding

    private val mainAdapter by lazy {
        MainAdapter()
    }

    private lateinit var presenter: SearchPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val dataSource = NewsDataSource(this@SearchActivity)
        presenter = SearchPresenter(this, dataSource)
        configRecycler()

        search()
        clickAdapter()
    }

    private fun search(){
//        binding.searchNews.clearFocus()
        binding.searchNews.setOnQueryTextListener(
            UtilQueryTextListener(
                this.lifecycle
            ) { newText ->
                newText?.let {query ->
                    if (query.isNotEmpty()) {
                        presenter.search(query)
                        binding.rvProgressBarSearch.visibility = View.VISIBLE
                    }
                }
            }
        )
    }

    private fun configRecycler(){
        with(binding.rvSearch) {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(this@SearchActivity)
            addItemDecoration(
                DividerItemDecoration(this@SearchActivity, DividerItemDecoration.VERTICAL)
            )
        }
    }

    private fun clickAdapter(){
        mainAdapter.setOnClickListener { article ->
            val intent = Intent(this, ArticleActivity::class.java)
            intent.putExtra("article", article)
            startActivity(intent)
        }
    }



    override fun showProgressBar() {
        binding.rvProgressBarSearch.visibility = View.VISIBLE
    }

    override fun showFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun hideProgressBar() {
        binding.rvProgressBarSearch.visibility = View.INVISIBLE
    }

    override fun showArticles(articles: List<Article>) {
        mainAdapter.differ.submitList(articles.toList())
    }

}