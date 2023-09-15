package com.joao.newsapp.model.data

import com.joao.newsapp.model.Article
import com.joao.newsapp.model.db.ArticleDataBase

class NewsRepository(private val db: ArticleDataBase) {

    suspend fun updateInsert(article: Article) = db.getArticleDao().updateInsert(article)

    fun getAll(): List<Article> = db.getArticleDao().getAll()

    suspend fun delete(article: Article) = db.getArticleDao().delete(article)
}