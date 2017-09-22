package io.github.rfonzi.rxaware.bus.events

/**
 * Created by ryan on 9/18/17.
 */
class NavigateUpEvent : Event {
    override val id: EventID
        get() = EventID.NAVIGATE_UP
}