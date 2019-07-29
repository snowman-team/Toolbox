package com.xueqiu.toolbox.scheduler

import android.os.Handler
import android.os.Message
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.plugins.RxJavaPlugins
import java.util.concurrent.TimeUnit

internal class MainHandlerWorker(private val mainHandler: Handler) : Scheduler.Worker() {

    @Volatile
    private var disposed: Boolean = false

    override fun schedule(runnable: Runnable, delay: Long, unit: TimeUnit): Disposable {

        if (disposed) {
            return Disposables.disposed()
        }
        val scheduled = ScheduledRunnable(mainHandler, RxJavaPlugins.onSchedule(runnable))
        val message = Message.obtain(mainHandler, scheduled)
        message.obj = this
        mainHandler.sendMessageDelayed(message, unit.toMillis(delay))
        if (disposed) {
            mainHandler.removeCallbacks(scheduled)
            return Disposables.disposed()
        }
        return scheduled
    }

    override fun dispose() {
        disposed = true
        mainHandler.removeCallbacksAndMessages(this)
    }

    override fun isDisposed(): Boolean {
        return disposed
    }
}