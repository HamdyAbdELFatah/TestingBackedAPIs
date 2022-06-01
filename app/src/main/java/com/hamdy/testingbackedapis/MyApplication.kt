package com.hamdy.testingbackedapis

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.core.os.HandlerCompat
import java.util.concurrent.*

class MyApplication : Application() {
    val executorService: ExecutorService = Executors.newFixedThreadPool(4)
    val mainThreadHandler: Handler = HandlerCompat.createAsync(Looper.getMainLooper())

    private val NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors()

    private val workQueue: BlockingQueue<Runnable> = LinkedBlockingQueue()

    private val KEEP_ALIVE_TIME = 1L
    private val KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS

    private val threadPoolExecutor: ThreadPoolExecutor = ThreadPoolExecutor(
        NUMBER_OF_CORES,
        NUMBER_OF_CORES,
        KEEP_ALIVE_TIME,
        KEEP_ALIVE_TIME_UNIT,
        workQueue
    )
}