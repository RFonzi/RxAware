package io.github.rfonzi.rxaware.bus.events

/**
 * Created by ryan on 9/21/17.
 */
data class ToastEvent(val message: String) : Event {
    override val id: EventID
        get() = EventID.TOAST
}