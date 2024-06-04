package com.example.composetutorial.workmananger

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class DownloadWorker(
    private val context: Context,
    params: WorkerParameters,
    private val downloadService: Service,
    private val notificationManager: NotificationManager
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        return Result.success()
    }

}