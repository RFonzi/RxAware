package io.github.rfonzi.rxaware

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowApplication

/**
 * Created by ryan on 9/25/17.
 */

@RunWith(RobolectricTestRunner::class)
class StartActivityEventTest {

    lateinit var testActivity: TestActivity

    @Before
    fun beforeEachTest(){
        testActivity = Robolectric.buildActivity(TestActivity::class.java).create().start().get()
    }

    @Test
    fun shouldStartTargetFromActivity(){
        val expected = Intent(testActivity, TargetActivity::class.java)
        testActivity.startActivity(expected)

        val actual = ShadowApplication.getInstance().nextStartedActivity

        assertEquals(expected.component, actual.component)

    }

    @Test
    fun shouldStartTargetFromViewModel(){
        val expected = Intent(testActivity, TargetActivity::class.java)
        testActivity.vm.startActivity(TargetActivity::class.java)

        val actual = ShadowApplication.getInstance().nextStartedActivity

        assertEquals(expected.component, actual.component)
    }

    @Test
    fun shouldStartTargetFromFragment(){
        val expected = Intent(testActivity, TargetActivity::class.java)
        val testFragment = TestFragment()

        testFragment.startActivity(TargetActivity::class.java)

        val actual = ShadowApplication.getInstance().nextStartedActivity

        assertEquals(expected.component, actual.component)
    }

    class TestActivity : RxAwareActivity(){
        lateinit var vm: TestViewModel

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            vm = ViewModelProviders.of(this).get(TestViewModel::class.java)
        }
    }
    class TargetActivity : RxAwareActivity()
    class TestFragment : RxAwareFragment()
    class TestViewModel : RxAwareViewModel()
}