package io.github.rfonzi.rxaware.bus

import com.jakewharton.rxrelay2.BehaviorRelay
import io.github.rfonzi.rxaware.bus.events.FlushEvent
import io.reactivex.Observable

/**
 * Created by ryan on 8/24/17.
 */
object RxBus {
    private val subject: BehaviorRelay<Any> = BehaviorRelay.create()

    fun post(event: Any) = subject.accept(event)

    fun <T> toObservable(eventType: Class<T>): Observable<T> = subject.ofType(eventType)

    fun clear() = post(FlushEvent())
}


//val questionBankRelayModule = Kodein.Module {
//    bind<RxBus>() with singleton { RxBus() }
//}