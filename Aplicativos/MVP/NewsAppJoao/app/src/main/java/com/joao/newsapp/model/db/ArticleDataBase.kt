package com.joao.newsapp.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.joao.newsapp.model.Article

@Database(entities = [Article::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ArticleDataBase: RoomDatabase() {

    abstract fun getArticleDao(): ArticleDao

    companion object {

        @Volatile
        private var INSTANCE: ArticleDataBase? = null
        private var Lock = Any()

        operator fun invoke(context: Context): ArticleDataBase = INSTANCE ?: synchronized(Lock) {
            INSTANCE ?: createdDatabase(context).also { articleDatabase ->
                INSTANCE = articleDatabase
            }
        }

        private fun createdDatabase(context: Context): ArticleDataBase {
            return Room.databaseBuilder(
                context.applicationContext,
                ArticleDataBase::class.java,
                "article_db.db"
            ).build()
        }


    }
}