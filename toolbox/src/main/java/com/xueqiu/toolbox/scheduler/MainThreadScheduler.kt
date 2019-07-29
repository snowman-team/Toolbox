package com.xueqiu.toolbox.scheduler

import android.os.Handler
import android.os.Looper
import android.os.Message
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.plugins.RxJavaPlugins
import java.util.concurrent.TimeUnit

internal class MainThreadScheduler : Scheduler() {

    private val mHandler = Handler(Looper.getMainLooper())

    override fun scheduleDirect(runnable: Runnable, delay: Long, unit: TimeUnit): Disposable {
        val scheduled = ScheduledRunnable(mHandler, RxJavaPlugins.onSchedule(runnable))
        val message = Message.obtain(mHandler, scheduled)
        mHandler.sendMessageDelayed(message, unit.toMillis(delay))
        return scheduled
    }

    override fun createWorker(): Worker = MainHandlerWorker(mHandler)

}