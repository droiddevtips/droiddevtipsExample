package com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.domain

import com.droiddevtips.multiscreensupport.R

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
fun Int.createListDetailItem(viewType: com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.data.ViewType): com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.data.ListDetailItem {

    return when(viewType) {

        _root_ide_package_.com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.data.ViewType.Home -> _root_ide_package_.com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.data.ListDetailItem(
            title = "Home item $this",
            description = "A well-crafted article description serves as a brief and engaging summary, enticing potential readers to delve into the full text. Its primary goal is to quickly convey the article's core message and relevance to the target audience. The ideal description is concise, informative, and tailored to the platform where it appears.\n\nFor online content, this description is often referred to as a meta description. It plays a crucial role in search engine optimization (SEO) by influencing click-through rates from search results. A good meta description is typically between 150 and 160 characters and includes relevant keywords to attract the right readers. It should be written in an active voice and often includes a call to action.\n\nIn academic or scientific contexts, the description takes the form of an abstract. An abstract is a more formal and structured summary of a research paper or scholarly article. It provides a comprehensive overview of the article's purpose, methods, key findings, and conclusions.",
            image = R.drawable.article_image
        )

        _root_ide_package_.com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.data.ViewType.News -> _root_ide_package_.com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.data.ListDetailItem(
            title = "Article $this",
            description = "A well-crafted article description serves as a brief and engaging summary, enticing potential readers to delve into the full text. Its primary goal is to quickly convey the article's core message and relevance to the target audience. The ideal description is concise, informative, and tailored to the platform where it appears.\n\nFor online content, this description is often referred to as a meta description. It plays a crucial role in search engine optimization (SEO) by influencing click-through rates from search results. A good meta description is typically between 150 and 160 characters and includes relevant keywords to attract the right readers. It should be written in an active voice and often includes a call to action.\n\nIn academic or scientific contexts, the description takes the form of an abstract. An abstract is a more formal and structured summary of a research paper or scholarly article. It provides a comprehensive overview of the article's purpose, methods, key findings, and conclusions.",
            image = R.drawable.article_image
        )

        _root_ide_package_.com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.data.ViewType.Follower -> {

            val followers = getFollowerAmount()

            _root_ide_package_.com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.data.ListDetailItem(
                title = "Follower $this",
                description = "${getFollowerAmount()} ${if (followers == 0) "no follower" else if (followers == 1) "follower" else "followers"}",
                image = R.drawable.article_image
            )
        }
    }
}

private fun getFollowerAmount(): Int {
    val range = 50..200
    return range.random()
}
