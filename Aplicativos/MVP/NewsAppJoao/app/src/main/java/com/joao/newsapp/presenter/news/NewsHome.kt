package com.joao.newsapp.presenter.news

import com.joao.newsapp.model.NewsResponse

interface NewsHome {

    interface Presenter{
        fun requestAll()
        fun onSuccess(newsResponse: NewsResponse)
        fun onError(message: String)
        fun onComplete()
    }

}
