package io.github.rfonzi.rxaware

import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

/**
 * Created by ryan on 10/4/17.
 */

@RunWith(RobolectricTestRunner::class)
class PostToCurrentActivityTest {

    lateinit var testActivity: TestActivity
    lateinit var testFragment: TestFragment

    @Before
    fun beforeEachTest(){
        testActivity = Robolectric.buildActivity(TestActivity::class.java).create().start().get()
        testFragment = TestFragment()
    }

    @Test
    fun shouldBeAbleToPost(){
        val testString = "hello"
        testFragment.postToCurrentActivity(testString)

        val testObserver = TestObserver<String>()
        testActivity.onPost<String>().subscribe(testObserver)

        testObserver.assertValue(testString)
    }

    @Test
    fun shouldBeAbleToReactToPost(){
        val testString = "hello"

        testActivity.onPost<String>()
                .subscribe { testActivity.test = it }

        testFragment.postToCurrentActivity(testString)

        assertEquals(testString, testActivity.test)
    }


    class TestActivity : RxAwareActivity(){
        var test = "nope"
    }
    class TestFragment : RxAwareFragment()
}