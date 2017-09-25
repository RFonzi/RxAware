package io.github.rfonzi.rxaware

import io.github.rfonzi.rxaware.bus.UIBus
import io.github.rfonzi.rxaware.bus.events.Event
import io.github.rfonzi.rxaware.bus.events.NavigateUpEvent
import io.github.rfonzi.rxaware.bus.events.ToastEvent
import io.reactivex.observers.TestObserver

import org.junit.Test
import org.junit.runner.RunWith


/**
 * Created by ryan on 9/22/17.
 */

class UIBusTest {

    @Test
    fun testSendingToast() {
        val expected = "testing"

        val testObserver = TestObserver<Event>()
        UIBus.toObservable()
                .subscribe(testObserver)
        UIBus.toast(expected)

        testObserver.assertValue(ToastEvent(expected))

    }

    @Test
    fun testSendingNavigateUp() {
        val testObserver = TestObserver<Event>()
        UIBus.toObservable()
                .subscribe(testObserver)
        UIBus.navigateUp()

        testObserver.assertValue { it is NavigateUpEvent }
    }

}