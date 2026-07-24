package com.hitanshi.newsheadlinestv

import android.app.Application
import com.hitanshi.newsheadlinestv.data.api.RetrofitClient
import com.hitanshi.newsheadlinestv.data.repository.NewsRepository
import com.hitanshi.newsheadlinestv.data.repository.NewsRepositoryImpl

class NewsApplication : Application() {

    val repository: NewsRepository by lazy {
        NewsRepositoryImpl(RetrofitClient.api)
    }
}