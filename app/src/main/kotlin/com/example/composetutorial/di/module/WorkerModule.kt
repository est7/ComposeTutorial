package com.example.composetutorial.di.module

import androidx.work.WorkerParameters
import com.example.composetutorial.workmananger.DownloadWorker
import org.koin.androidx.workmanager.dsl.worker
import org.koin.dsl.module

/**
 *
 * @author: est8
 * @date: 2024/6/4
 */
val workerModule = module {
    worker { (workerParams: WorkerParameters) ->
        DownloadWorker(
            context = get(), params = workerParams, downloadService = get(), notificationManager = get()
        )
    }
}