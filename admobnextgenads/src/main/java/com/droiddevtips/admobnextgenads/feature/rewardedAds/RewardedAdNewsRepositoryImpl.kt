package com.droiddevtips.admobnextgenads.feature.rewardedAds

import com.droiddevtips.admobnextgenads.R
import kotlinx.coroutines.delay
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
class RewardedAdNewsRepositoryImpl: RewardedAdNewsRepository {

    override suspend fun loadNews(): List<RewardedAdListDisplayItem> {
        delay(2.seconds) // Simulate remote server api call
        return getNewsListData()
    }

    private fun getNewsListData(): List<RewardedAdListDisplayItem> {

        return ArrayList<RewardedAdListDisplayItem>().apply {
            for (item in 1..50) {
                add(RewardedAdListDisplayItem(image = getRandomFeatureImage(),title = "News article $item", description = "The description for article $item"))

                if (item % 5 == 0) {
                    add(RewardedAdListDisplayItem(image = getRandomPremiumFeatureImage(), title = "Premium article $item", "Requires 1 credit to read premium article $item", premium = true))
                }
            }
        }
    }

    private fun getRandomFeatureImage(): Int {

        val imageIDs = listOf(
            R.mipmap.image1,
            R.mipmap.image2,
            R.mipmap.image3,
            R.mipmap.image4
        )

        val randomIndex = Random.nextInt(imageIDs.size)

        return imageIDs[randomIndex]
    }

    private fun getRandomPremiumFeatureImage(): Int {

        val imageIDs = listOf(
            R.mipmap.image5,
            R.mipmap.image6,
        )

        val randomIndex = Random.nextInt(imageIDs.size)

        return imageIDs[randomIndex]
    }
}