package io.github.rfonzi.rxaware_sample

import io.github.rfonzi.rxaware.BaseViewModel
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import java.util.*

/**
 * Created by ryan on 9/22/17.
 */

class MainViewModel : BaseViewModel() {

    private val random = Random(Calendar.DATE.toLong())
    private var onTop = true

    var score = 0
    val maxScore: Long = 1000
    private var height = 0
    private var width = 0


    private val coordinates: BehaviorSubject<Pair<Int, Int>> = BehaviorSubject.create()
    private val win: BehaviorSubject<Boolean> = BehaviorSubject.create()

    fun coordinates(): Observable<Pair<Int, Int>> = coordinates
    fun winSignal(): Observable<Boolean> = win



    fun exposePuck(clicks: Observable<Unit>) = clicks
            .subscribe {
                coordinates.onNext(nextCoords())
                onTop = !onTop
            }
            .lifecycleAware()

    fun exposeDimens(dimens: Observable<Pair<Int, Int>>) = dimens
            .subscribe {
                width = it.first
                height = it.second
                score++
                if (score >= maxScore)
                    win.onNext(true)
            }
            .lifecycleAware()

    private fun nextCoords(): Pair<Int, Int> {
        var x = random.nextInt(width)
        var y = random.nextInt(height)

        if (height > width)
            y /= 2
        else
            x /= 2

        if(!onTop)
            y += height.div(2)

        return x to y
    }

}