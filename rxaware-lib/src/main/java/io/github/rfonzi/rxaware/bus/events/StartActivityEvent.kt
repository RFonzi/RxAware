package io.github.rfonzi.rxaware.bus.events

import android.support.v7.app.AppCompatActivity

/**
 * Created by ryan on 9/25/17.
 */
data class StartActivityEvent(val activity: Class<out AppCompatActivity>) : Event {
    override val id: EventID
        get() = EventID.START_ACTIVITY
}