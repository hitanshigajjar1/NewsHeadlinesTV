package com.hitanshi.newsheadlinestv.data.repository

import com.hitanshi.newsheadlinestv.data.api.NewsApi
import com.hitanshi.newsheadlinestv.data.model.Article
import com.hitanshi.newsheadlinestv.data.model.toArticles
import com.hitanshi.newsheadlinestv.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.supervisorScope

class NewsRepositoryImpl(
    private val api: NewsApi
) : NewsRepository {

    override suspend fun getHeadlines(): List<Article> =
        supervisorScope {

            val jobs = Constants.CATEGORIES.map { category ->

                async(Dispatchers.IO) {

                    api.getNews(
                        country = Constants.COUNTRY,
                        category = category,
                        pageSize = Constants.PAGE_SIZE,
                        apiKey = Constants.API_KEY
                    ).toArticles(category)

                }

            }

            jobs.awaitAll()
                .flatten()
                .distinctBy { it.url }

        }

}