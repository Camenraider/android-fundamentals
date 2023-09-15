package com.joao.newsapp.presenter.favorite

import com.joao.newsapp.model.Article
import com.joao.newsapp.model.data.NewsDataSource
import com.joao.newsapp.presenter.ViewHome

class FavoritePresenter(
    val view: ViewHome.Favorite,
    private val dataSource: NewsDataSource) : FavoriteHome.Presenter {

    fun getAll(){
        this.dataSource.getAllArticle(this)
    }

    fun saveArticle(article: Article) {
        this.dataSource.saveArticle(article)
    }

    fun deleteArtcle(article: Article) {
        this.dataSource.deleteArticle(article)
    }

    override fun onSuccess(articles: List<Article>) {
        this.view.showArticles(articles)
    }
}