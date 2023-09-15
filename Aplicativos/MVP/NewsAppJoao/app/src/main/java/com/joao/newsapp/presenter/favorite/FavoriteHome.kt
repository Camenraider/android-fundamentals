package com.joao.newsapp.presenter.favorite

import com.joao.newsapp.model.Article

interface FavoriteHome {

    interface Presenter {
        fun onSuccess(article: List<Article>)
    }
}