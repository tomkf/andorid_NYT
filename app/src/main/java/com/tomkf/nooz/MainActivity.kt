package com.tomkf.nooz

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tomkf.nooz.network.Article
import com.tomkf.nooz.network.NewsAPI
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_article.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.compat.ScopeCompat.viewModel
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    private val articlesViewModel: ArticlesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        article_list.layoutManager = GridLayoutManager(this, 2)

        articlesViewModel.loadPopularArticles()

        articlesViewModel.articles.observe(this, Observer { articles ->
            articles ?: return@Observer
            article_list.adapter = ArticleAdaptor(articles, this)
        })

        swipe_refresh_articles_layout.setOnRefreshListener {
            articlesViewModel.loadPopularArticles()
            swipe_refresh_articles_layout.isRefreshing = false
        }
    }
}


private class ArticleAdaptor(val articles: List<Article>, val context: Context) : RecyclerView.Adapter<ArticleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_article,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return articles.count()
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.itemView.item_title.text = article.title

        article.media.firstOrNull()?.mediaMetadata?.firstOrNull()?.url.let {
            Glide.with(context).load(it).into(holder.itemView.item_image)
        }
    }
}


private class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view)