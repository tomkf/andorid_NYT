package com.tomkf.nooz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.tomkf.nooz.network.Article
import com.tomkf.nooz.network.API
import com.tomkf.nooz.network.ArticleResponse
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    // This is our coroutine (by adding GlobalScope launch Main)
    suspend fun getPopularArticles() : List<Article>? {
        return try {
            val response = api.getPopularArticlesAsync(BuildConfig.API_KEY)
            response.results
        } catch (e: Exception) {
            Log.e(e.toString(), e.localizedMessage)
            null
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // We will use a coroutine to help us simplify the asynchronous code
        getPopularArticles()
    }
}