package com.droiddevtips.multiscreensupport.common.extensions

import com.droiddevtips.multiscreensupport.R
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data.ListDetailItem
import com.droiddevtips.multiscreensupport.viewmodelFactoryExample.listDetailItem.data.ViewType

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */

fun Int.toListDetailItem(viewType: ViewType): ListDetailItem {

    return if (viewType == ViewType.Follower) {

        val followers = (50..200).random()

        ListDetailItem(
            title = "Follower $this",
            description = " ${if (followers == 0) "no followers" else if (followers == 1) "${followers} follower" else "${followers} followers"}",
            image = R.drawable.follower,
            viewType = viewType
        )
    } else if (viewType == ViewType.News) {
        ListDetailItem(
            title = "News $this",
            description = "Ever wanted to bake the perfect loaf of sourdough bread but felt too intimidated to start? This comprehensive guide breaks down the entire process into simple, manageable steps. From creating your own starter from scratch to achieving that perfect crispy crust and airy crumb, we'll walk you through everything you need to know. By the end of this article, you'll have the confidence and skills to bake a delicious, rustic loaf right in your own kitchen.",
            image = R.drawable.article_image,
            viewType = viewType
        )
    } else {
        ListDetailItem(
            title = "Article $this",
            description = "Artificial intelligence is no longer just for data analysis; it's now creating art, writing music, and designing products. This article explores the rapid integration of AI into creative industries and what it means for artists, writers, and designers. We analyze the latest AI advancements, feature insights from industry experts, and tackle the pressing question: will AI become a powerful collaborator, or will it replace human creativity altogether? Join us as we unpack the future of creative work",
            image = R.drawable.article_image,
            viewType = viewType
        )
    }
}