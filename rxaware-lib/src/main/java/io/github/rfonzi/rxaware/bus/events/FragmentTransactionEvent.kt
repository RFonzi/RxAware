package io.github.rfonzi.rxaware.bus.events

import android.support.v4.app.FragmentTransaction

/**
 * Created by ryan on 9/21/17.
 */
data class FragmentTransactionEvent(val event: FragmentTransaction.() -> Unit): Event {
    override val id: EventID
        get() = EventID.FRAGMENT_TRANSACTION
}