package com.droiddevtips.floatingtabbarandpip.feature.videos.ui

import com.droiddevtips.floatingtabbarandpip.common.videoList.ui.VideoListViewModel
import com.droiddevtips.floatingtabbarandpip.common.videoList.data.videoRepository.VideoRepository

/**
 * The video view model
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
class VideosViewModel(
    repository: VideoRepository
) : VideoListViewModel(favorite = false, repository = repository)