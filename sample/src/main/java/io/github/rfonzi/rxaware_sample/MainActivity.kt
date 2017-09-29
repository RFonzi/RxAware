package io.github.rfonzi.rxaware_sample

import android.arch.lifecycle.ViewModelProviders
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.jakewharton.rxbinding2.view.clicks
import io.github.rfonzi.rxaware.RxAwareActivity
import io.reactivex.Observable

class MainActivity : RxAwareActivity() {

    lateinit var vm: MainViewModel
    lateinit var puck: ImageButton
    lateinit var score: TextView
    lateinit var win1: TextView
    lateinit var win2: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        vm = ViewModelProviders.of(this).get(MainViewModel::class.java)

        puck = findViewById(R.id.puck)
        score = findViewById(R.id.score)
        win1 = findViewById(R.id.win1)
        win2 = findViewById(R.id.win2)


        vm.exposePuck(puck.clicks())
        vm.exposeDimens(getDimens())

        vm.coordinates()
                .subscribe {
                    puck.x = it.first.toFloat() - (puck.width.div(2))
                    puck.y = it.second.toFloat() - (puck.height.div(2))
                }
                .lifecycleAware()

        vm.winSignal()
                .filter { it }
                .subscribe {
                    puck.visibility = View.GONE
                    score.setTextColor(Color.BLACK)
                    win1.visibility = View.VISIBLE
                    win2.visibility = View.VISIBLE
                }
                .lifecycleAware()

        vm.getScore()
                .subscribe {
                    score.text = it.toString()
                }
                .lifecycleAware()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        vm.exposeDimens(getDimens())

    }

    fun getDimens(): Observable<Pair<Int, Int>>{
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        return Observable.fromCallable { metrics.widthPixels to metrics.heightPixels }
    }


}
