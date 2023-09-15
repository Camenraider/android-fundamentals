package com.joao.newsapp.model.data

import android.content.Context
import com.joao.newsapp.model.Article
import com.joao.newsapp.model.db.ArticleDataBase
import com.joao.newsapp.network.RetrofitInstance
import com.joao.newsapp.presenter.favorite.FavoriteHome
import com.joao.newsapp.presenter.news.NewsHome
import com.joao.newsapp.presenter.search.SearchHome
import kotlinx.coroutines.*

class NewsDataSource(context: Context) {

    private var db: ArticleDataBase = ArticleDataBase(context)
    private var newsRepository: NewsRepository = NewsRepository(db)

    @OptIn(DelicateCoroutinesApi::class)
    fun getBreakingNews(callback: NewsHome.Presenter) {
        GlobalScope.launch(Dispatchers.Main) {
            val response = RetrofitInstance.api.getBreakingNews("BR")
            if (response.isSuccessful) {
                response.body()?.let { newsResponse ->
                    callback.onSuccess(newsResponse)
                }
                callback.onComplete()
            } else {
                callback.onError(response.message())
                callback.onComplete()
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun searchNews(term: String, callback: SearchHome.Presenter) {
        GlobalScope.launch(Dispatchers.Main) {
            val response = RetrofitInstance.api.searchNews(term)
            if (response.isSuccessful) {
                response.body()?.let { newsResponse ->
                    callback.onSuccess(newsResponse)
                }
                callback.onComplete()
            } else {
                callback.onError(response.message())
                callback.onComplete()
            }
        }
    }


    @OptIn(DelicateCoroutinesApi::class)
    fun saveArticle(article: Article) {
        GlobalScope.launch(Dispatchers.Main) {
            newsRepository.updateInsert(article)
        }
    }

    fun getAllArticle(callback: FavoriteHome.Presenter) {
        var allArticles: List<Article>
        CoroutineScope(Dispatchers.IO).launch {
            allArticles = newsRepository.getAll()

            withContext(Dispatchers.Main) {
                callback.onSuccess(allArticles)
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun deleteArticle(article: Article?) {
        GlobalScope.launch(Dispatchers.Main) {
            article?.let { articleSafe ->
                newsRepository.delete(articleSafe)
            }
        }
    }

}