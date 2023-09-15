package com.joao.newsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.joao.newsapp.R
import com.joao.newsapp.databinding.ItemNewsBinding
import com.joao.newsapp.model.Article

class MainAdapter: RecyclerView.Adapter<MainAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(val binding: ItemNewsBinding): RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
//        return ArticleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false))
        return ArticleViewHolder(ItemNewsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {

            with(holder){
                with(differ.currentList[position]){
                    Glide.with(holder.itemView.context).load(urlToImage).into(binding.ivArticleImage)

                    binding.apply {
                        tvTitle.text = author ?: source?.name
                        tvSource.text = source?.name ?: author
                        tvDescription.text = description
                        tvPublishedAt.text = publishedAt
                    }

                    itemView.setOnClickListener {
                        onItemClickListener?.let { click ->
                            click(this)
                        }
                    }
                }
            }

    }


    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }
}