package cn.com.uama.flashlightcompat

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable

/**
 * Created by liwei on 2018/1/25 15:34
 * Email: liwei@uama.com.cn
 * Description: 通过 RxJava2 实现的 EventBus
 * 具体用到了 Jake Wharton 的 [RxRelay](https://github.com/JakeWharton/RxRelay)
 */
object RxBus {
    private val bus = PublishRelay.create<Any>().toSerialized()

    fun send(event: Any) {
        bus.accept(event)
    }

    fun <T> toFlowable(eventType: Class<T>): Flowable<T> {
        return bus.toFlowable(BackpressureStrategy.LATEST)
                .ofType(eventType)
    }

    fun <T> toObservable(eventType: Class<T>): Observable<T> {
        return bus.ofType(eventType)
    }

    fun hasObservers(): Boolean {
        return bus.hasObservers()
    }
}