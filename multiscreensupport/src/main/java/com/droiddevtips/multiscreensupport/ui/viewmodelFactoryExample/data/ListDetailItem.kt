package com.droiddevtips.multiscreensupport.ui.viewmodelFactoryExample.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Melchior Vrolijk
 * Droid Dev Tips (c) 2025. All rights reserved.
 */
@Parcelize
data class ListDetailItem(val title:String, val description:String, val image:Int): Parcelable
