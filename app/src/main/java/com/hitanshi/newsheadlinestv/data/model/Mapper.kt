package com.hitanshi.newsheadlinestv.data.model

fun NewsResponse.toArticles(category: String): List<Article> {

    return articles.map {

        Article(

            id = it.url.hashCode().toString(),

            title = it.title ?: "No Title",

            description = it.description ?: "No Description",

            imageUrl = it.urlToImage,

            source = it.source?.name ?: "Unknown",

            publishedAt = it.publishedAt ?: "",

            url = it.url ?: "",

            category = category

        )

    }

}