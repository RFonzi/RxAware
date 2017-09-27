package io.github.rfonzi.rxaware

import io.github.rfonzi.rxaware.bus.UIBus
import org.junit.Test
import org.junit.Assert.*

/**
 * Created by ryan on 9/26/17.
 */


class StoreAndReceiveEventTest{

    @Test
    fun shouldBeAbleToStoreData(){
        val test = "Test string"

        UIBus.store(test)

        assertEquals(test, UIBus.receive() as String)
    }

}