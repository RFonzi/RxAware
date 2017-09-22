package io.github.rfonzi.rxaware_sample

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.ImageButton
import com.jakewharton.rxbinding2.view.clicks
import io.github.rfonzi.rxaware.BaseActivity
import io.reactivex.Observable

class MainActivity : BaseActivity() {

    lateinit var vm: MainViewModel
    lateinit var puck: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        vm = ViewModelProviders.of(this).get(MainViewModel::class.java)

        puck = findViewById(R.id.puck)

        vm.exposePuck(puck.clicks())

        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        val dimens = Observable.fromCallable { metrics.widthPixels to metrics.heightPixels }


        vm.exposeDimens(dimens)

        vm.coordinates()
                .subscribe {
                    puck.x = it.first.toFloat() - (puck.width / 2)
                    puck.y = it.second.toFloat() - (puck.height / 2)
                }
                .lifecycleAware()

        vm.winSignal()
                .subscribe {
                    puck.visibility = View.GONE
                }
                .lifecycleAware()
    }


}
