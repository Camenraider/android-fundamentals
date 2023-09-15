package com.joao.newsapp.ui

import android.content.ClipData.Item
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.joao.newsapp.R
import com.joao.newsapp.adapter.MainAdapter
import com.joao.newsapp.databinding.ActivityFavoriteBinding
import com.joao.newsapp.model.Article
import com.joao.newsapp.model.data.NewsDataSource
import com.joao.newsapp.presenter.ViewHome
import com.joao.newsapp.presenter.favorite.FavoritePresenter

class FavoriteActivity : AppCompatActivity(), ViewHome.Favorite {

    private lateinit var binding: ActivityFavoriteBinding

    private val mainAdapter by lazy {
        MainAdapter()
    }

    private lateinit var presenter: FavoritePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val dataSource = NewsDataSource(this)
        presenter = FavoritePresenter(this, dataSource)
        presenter.getAll()
        configRecycle()
        clickAdapter()

        val itemTouchPerCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = mainAdapter.differ.currentList[position]
                presenter.deleteArtcle(article)
                Snackbar.make(
                    viewHolder.itemView, R.string.article_delete_successful,
                    Snackbar.LENGTH_SHORT
                ).apply {
                    setAction(getString(R.string.undo)){
                        presenter.saveArticle(article)
                        mainAdapter.notifyDataSetChanged()
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchPerCallback).apply {
            attachToRecyclerView(binding.rvFavorite)
        }

    }

    private fun configRecycle(){
        with(binding.rvFavorite){
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            addItemDecoration(DividerItemDecoration(this@FavoriteActivity, androidx.recyclerview.widget.DividerItemDecoration.VERTICAL))
        }
    }

    private fun clickAdapter(){
        mainAdapter.setOnClickListener { article ->
            val intent = Intent(this,ArticleActivity::class.java)
            intent.putExtra("article", article)
            startActivity(intent)
        }
    }


    override fun showArticles(articles: List<Article>) {
        mainAdapter.differ.submitList(articles.toList())
    }


}