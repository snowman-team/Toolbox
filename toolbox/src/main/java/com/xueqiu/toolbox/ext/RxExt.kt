package com.xueqiu.toolbox.ext

import com.xueqiu.toolbox.scheduler.MainSchedulers
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

fun <T> Observable<T>?.subscribeSafely() = this?.subscribe({}, {})

fun <T> Observable<T>?.subscribeSafely(consumer: Consumer<T>) =
        this?.subscribe(consumer, Consumer<Throwable> { })

inline fun <reified T> Observable<T>.applyNetworkSchedulers(): Observable<T> {
    return this.subscribeOn(Schedulers.io())
            .observeOn(MainSchedulers.mainThread)
}

inline fun <reified T> Observable<T>.applyComputationSchedulers(): Observable<T> {
    return this.subscribeOn(Schedulers.computation()).observeOn(MainSchedulers.mainThread)
}

inline fun <reified T> Observable<T>.intervalRequest(duration: Long): Observable<T> {
    return this.throttleFirst(duration, TimeUnit.MILLISECONDS)
}