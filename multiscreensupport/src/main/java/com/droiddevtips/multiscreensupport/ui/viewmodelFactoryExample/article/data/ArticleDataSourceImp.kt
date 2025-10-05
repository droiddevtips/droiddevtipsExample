package com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.article.data

import com.droiddevtips.multiscreensupport.R
import com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.article.data.Article
import kotlinx.coroutines.delay

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
class ArticleDataSourceImp: ArticleDataSource {

    override suspend fun loadArticles(): List<Article> {
        delay(300) // Simulate remote api call
        return demoArticles()
    }

    private fun demoArticles(): List<Article> {

        val articleList = ArrayList<Article>()

        for (i in 1..50) {
            articleList.add(createArticle(number = i))
        }

        return articleList
    }

    private fun createArticle(number:Int): Article {
        return Article(
            title = "Article $number",
            description = "A well-crafted article description serves as a brief and engaging summary, enticing potential readers to delve into the full text. Its primary goal is to quickly convey the article's core message and relevance to the target audience. The ideal description is concise, informative, and tailored to the platform where it appears.\n\nFor online content, this description is often referred to as a meta description. It plays a crucial role in search engine optimization (SEO) by influencing click-through rates from search results. A good meta description is typically between 150 and 160 characters and includes relevant keywords to attract the right readers. It should be written in an active voice and often includes a call to action.\n\nIn academic or scientific contexts, the description takes the form of an abstract. An abstract is a more formal and structured summary of a research paper or scholarly article. It provides a comprehensive overview of the article's purpose, methods, key findings, and conclusions.",
            image = R.drawable.article_image
        )
    }
}