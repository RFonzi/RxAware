package io.github.rfonzi.rxaware_sample

import java.util.*

/**
 * Created by ryan on 9/22/17.
 */
class PuckGameManager {

    var score = 0
    val maxScore = 50
    var prevHeight = 0
    var prevWidth = 0
    var height = 0
    var width = 0
    var win = false

    private var onTop = true
    private val random = Random(Calendar.DATE.toLong())

    fun incrementScore(){
        score++
        onTop = !onTop

        if (score >= maxScore)
            win = true
    }

    fun nextCoords(): Pair<Int, Int> {
        val x: Int = random.nextInt(width)
        var y: Float = random.nextInt(height).toFloat().div(height)

        y /= 2

        if (!onTop){
            y += .5f
        }

        return x to Math.round(y * height)
    }

    fun updateCoords(coords: Pair<Int, Int>): Pair<Int, Int>{
        val x = coords.first.toFloat().div(prevWidth)
        var y = coords.second.toFloat().div(prevHeight)

        return Math.round(x * width) to Math.round(y * height)
    }

    fun updateGameDimens(xy: Pair<Int, Int>) {
        prevWidth = width
        prevHeight = height
        width = xy.first
        height = xy.second
    }
}