package com.joao.newsapp.presenter

import com.joao.newsapp.model.Article

interface ViewHome {

    interface View{
        fun showProgressBar()
        fun showFailure(message: String)
        fun hideProgressBar()
        fun showArticles(articles: List<Article>)
    }

    interface Favorite {
        fun showArticles(articles: List<Article>)
    }
}