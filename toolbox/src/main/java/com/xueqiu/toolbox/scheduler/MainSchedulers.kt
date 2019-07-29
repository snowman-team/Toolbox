package com.xueqiu.toolbox.scheduler

import io.reactivex.Scheduler
import io.reactivex.exceptions.Exceptions
import java.util.concurrent.Callable

object MainSchedulers {

    val mainThread = initMainThreadScheduler(Callable { MainThreadScheduler() })

    private fun initMainThreadScheduler(scheduler: Callable<Scheduler>?): Scheduler {
        if (scheduler == null) {
            throw NullPointerException("scheduler == null")
        }
        return callRequire(scheduler)
    }

    private fun callRequire(scheduler: Callable<Scheduler>): Scheduler {
        try {
            return scheduler.call()
                    ?: throw NullPointerException("Scheduler Callable returned null")
        } catch (throwable: Throwable) {
            throw Exceptions.propagate(throwable)
        }
    }
}