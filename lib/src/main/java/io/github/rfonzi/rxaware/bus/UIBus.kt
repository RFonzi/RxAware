package io.github.rfonzi.rxaware.bus

import android.support.v4.app.FragmentTransaction
import com.jakewharton.rxrelay2.PublishRelay
import io.github.rfonzi.rxaware.bus.events.*
import io.reactivex.Observable

/**
 * Created by ryan on 9/21/17.
 */
object UIBus {
    private val subject: PublishRelay<Event> = PublishRelay.create()

    fun toast(message: String) = post(ToastEvent(message))

    fun navigateUp() = post(NavigateUpEvent())

    fun fragmentTransaction(transaction: FragmentTransaction.() -> Unit) = post(FragmentTransactionEvent(transaction))

    fun toObservable(): Observable<Event> = subject

    private fun post(event: Event) = subject.accept(event)
}

