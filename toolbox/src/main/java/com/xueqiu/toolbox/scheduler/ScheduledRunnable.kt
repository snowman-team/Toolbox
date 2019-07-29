package com.xueqiu.toolbox.scheduler

import android.os.Handler

import io.reactivex.disposables.Disposable
import io.reactivex.plugins.RxJavaPlugins

internal class ScheduledRunnable(private val handler: Handler, private val delegate: Runnable) : Runnable, Disposable {

    @Volatile
    private var mDisposed: Boolean = false

    override fun run() {
        try {
            delegate.run()
        } catch (t: Throwable) {
            RxJavaPlugins.onError(t)
        }
    }

    override fun dispose() {
        handler.removeCallbacks(this)
        mDisposed = true
    }

    override fun isDisposed(): Boolean {
        return mDisposed
    }
}