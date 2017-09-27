package io.github.rfonzi.rxaware.bus

import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import com.jakewharton.rxrelay2.PublishRelay
import io.github.rfonzi.rxaware.bus.events.*
import io.reactivex.Observable

/**
 * Created by ryan on 9/21/17.
 */
object UIBus {

    private var data: Any? = null
    private val subject: PublishRelay<Event> = PublishRelay.create()

    fun toast(message: String) = post(ToastEvent(message))

    fun navigateUp() = post(NavigateUpEvent())

    fun fragmentTransaction(transaction: FragmentTransaction.() -> Unit) = post(FragmentTransactionEvent(transaction))

    fun startActivity(target: Class<out AppCompatActivity>) = post(StartActivityEvent(target))

    fun startActivityAndStore(target: Class<out AppCompatActivity>, data: Any) {
        startActivity(target)
        store(data)
    }

    fun store(data: Any) {
        this.data = data
    }

    fun receive(): Any = requireNotNull(data) {
        "Can't receive a null value"
    }

    fun toObservable(): Observable<Event> = subject

    private fun post(event: Event) = subject.accept(event)
}

