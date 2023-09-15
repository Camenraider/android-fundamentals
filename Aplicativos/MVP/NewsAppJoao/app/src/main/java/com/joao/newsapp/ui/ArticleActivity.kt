package com.joao.newsapp.ui

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.joao.newsapp.R
import com.joao.newsapp.databinding.ActivityArticleBinding
import com.joao.newsapp.model.Article
import com.joao.newsapp.model.data.NewsDataSource
import com.joao.newsapp.presenter.ViewHome
import com.joao.newsapp.presenter.favorite.FavoritePresenter

class ArticleActivity : AppCompatActivity(), ViewHome.Favorite {

    private lateinit var binding: ActivityArticleBinding

    private lateinit var article: Article
    private lateinit var presenter: FavoritePresenter


    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val dataSource = NewsDataSource(this)
        presenter = FavoritePresenter(this, dataSource)

        var article = getArticle()

//        binding.webView.loadUrl(article.url.toString())
        binding.webView.apply {
            webViewClient = WebViewClient()
            article.url?.let {
                loadUrl(article.url.toString())
                settings.javaScriptEnabled = true
            }
        }
        fabClick()
    }

    private fun fabClick() {
        binding.fab.setOnClickListener {
            presenter.saveArticle(article)
            Snackbar.make(
                it,
                R.string.article_saved_successful,
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    private fun getArticle(): Article {
        intent.extras?.let { articleSend ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                article = articleSend.getSerializable("article", Article::class.java)!!
            } else {
                article = articleSend.get("article") as Article
            }
        }
        return article
    }


    override fun showArticles(articles: List<Article>) {}

}