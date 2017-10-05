package io.github.rfonzi.rxaware.bus.events

/**
 * Created by ryan on 10/4/17.
 */
class PostToCurrentActivityEvent(val post: Any) : Event {
    override val id: EventID
        get() = EventID.POST_TO_CURRENT_ACTIVITY
}