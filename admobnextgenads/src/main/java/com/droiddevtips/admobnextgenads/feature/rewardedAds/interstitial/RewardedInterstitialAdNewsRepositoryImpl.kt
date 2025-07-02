package com.droiddevtips.admobnextgenads.feature.rewardedAds.interstitial

import com.droiddevtips.admobnextgenads.R
import com.droiddevtips.admobnextgenads.feature.rewardedAds.rewarded.data.RewardedAdListDisplayItem
import kotlinx.coroutines.delay
import java.util.UUID
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
class RewardedInterstitialAdNewsRepositoryImpl: RewardedInterstitialAdNewsRepository {

    override suspend fun loadRewardedInterstitialDummyNews(): List<RewardedAdListDisplayItem> {
        delay(2.seconds) // Simulate remote server api call
        return getNewsListData()
    }

    private fun getNewsListData(): List<RewardedAdListDisplayItem> {

        return ArrayList<RewardedAdListDisplayItem>().apply {
            for (item in 1..50) {
                add(
                    RewardedAdListDisplayItem(
                        imageID = "$item",
                        image = getRandomFeatureImage(),
                        title = "News article $item",
                        titleID = UUID.randomUUID().toString(),
                        description = "The description for article $item"
                    )
                )

                if (item % 5 == 0) {
                    add(
                        RewardedAdListDisplayItem(
                            imageID = "${UUID.randomUUID()}$item",
                            image = getRandomPremiumFeatureImage(),
                            title = "Premium article",
                            titleID = "${UUID.randomUUID()}$item",
                            description = "Requires 1 credit to read premium article $item",
                            premium = true
                        )
                    )
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