package com.example.composetutorial.utils

import com.example.composetutorial.R
import java.util.concurrent.TimeUnit

/**
 *
 * @author: est8
 * @date: 2024/6/27
 */

object MyDateUtils {
    fun getTimePassedInHourMinSec(
        resourceProvider: ResourceProviderUtils, timePassedMs: Long
    ): String {
        return when {
            timePassedMs < TimeUnit.MINUTES.toMillis(1) -> {
                resourceProvider.getString(
                    R.string.d_seconds_ago, TimeUnit.MILLISECONDS.toSeconds(timePassedMs)
                )
            }

            timePassedMs < TimeUnit.HOURS.toMillis(1) -> {
                resourceProvider.getString(
                    R.string.d_minutes_ago, TimeUnit.MILLISECONDS.toMinutes(timePassedMs)
                )
            }

            timePassedMs < TimeUnit.HOURS.toMillis(4) -> {
                val hours = TimeUnit.MILLISECONDS.toHours(timePassedMs)
                val minutes =
                    TimeUnit.MILLISECONDS.toMinutes(timePassedMs - hours * TimeUnit.HOURS.toMillis(1))
                resourceProvider.getString(R.string.d_hours_ago, hours, minutes)
            }

            else -> ""
        }
    }

}