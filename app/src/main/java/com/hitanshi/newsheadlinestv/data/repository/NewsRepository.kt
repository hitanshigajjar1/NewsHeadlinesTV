package com.hitanshi.newsheadlinestv.data.repository

import com.hitanshi.newsheadlinestv.data.model.Article

interface NewsRepository {

    suspend fun getHeadlines(): List<Article>

}