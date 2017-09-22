package io.github.rfonzi.rxaware_sample

import io.github.rfonzi.rxaware.BaseViewModel
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import java.util.*

/**
 * Created by ryan on 9/22/17.
 */

class MainViewModel : BaseViewModel() {

    private val game = PuckGameManager()
    
    private val coordinates: BehaviorSubject<Pair<Int, Int>> = BehaviorSubject.create()
    private val scoreObservable: BehaviorSubject<Int> = BehaviorSubject.create()
    private val win: BehaviorSubject<Boolean> = BehaviorSubject.create()

    fun coordinates(): Observable<Pair<Int, Int>> = coordinates
    fun winSignal(): Observable<Boolean> = win
    fun getScore(): Observable<Int> = scoreObservable


    fun exposePuck(clicks: Observable<Unit>) = clicks
            .subscribe {
                coordinates.onNext(game.nextCoords())
                scoreObservable.onNext(game.score)

                if(game.win){
                    win.onNext(true)
                    toast("Winner Winner Chicken Dinner")
                }
            }
            .lifecycleAware()

    fun exposeDimens(dimens: Observable<Pair<Int, Int>>) = dimens
            .subscribe {
                game.updateGameDimens(it)
            }
            .lifecycleAware()

}