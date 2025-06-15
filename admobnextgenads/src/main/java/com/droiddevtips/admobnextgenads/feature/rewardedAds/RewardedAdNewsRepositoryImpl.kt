package com.droiddevtips.admobnextgenads.feature.rewardedAds

import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
class RewardedAdNewsRepositoryImpl: RewardedAdNewsRepository {

    override suspend fun loadNews(): List<RewardedAdListItem> {
        delay(2.seconds) // Simulate remote server api call
        return getNewsListData()
    }

    private fun getNewsListData(): List<RewardedAdListItem> {

        return ArrayList<RewardedAdListItem>().apply {
            for (item in 1..50) {
                add(RewardedAdListItem(title = "News item $item"))

                if (item % 5 == 0) {
                    add(RewardedAdListItem(title = "News item $item", premium = true))
                }
            }
        }
    }
}