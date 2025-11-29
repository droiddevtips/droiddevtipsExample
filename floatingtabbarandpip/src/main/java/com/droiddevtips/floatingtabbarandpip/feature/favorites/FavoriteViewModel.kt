package com.droiddevtips.floatingtabbarandpip.feature.favorites

import com.droiddevtips.floatingtabbarandpip.common.videoList.ui.VideoListViewModel
import com.droiddevtips.floatingtabbarandpip.common.videoList.data.videoRepository.VideoRepository

/**
 * The favorite view model
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
class FavoriteViewModel(repository: VideoRepository): VideoListViewModel(favorite = true, repository = repository)