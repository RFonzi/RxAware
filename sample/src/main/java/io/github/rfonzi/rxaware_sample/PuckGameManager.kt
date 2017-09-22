package io.github.rfonzi.rxaware_sample

import java.util.*

/**
 * Created by ryan on 9/22/17.
 */
class PuckGameManager {

    var score = 0
    val maxScore = 50
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
        var x = random.nextInt(width)
        var y = random.nextInt(height)

        if (height > width)
            y /= 2
        else
            x /= 2

        if (!onTop)
            y += height.div(2)

        return x to y
    }

    fun updateGameDimens(xy: Pair<Int, Int>) {
        width = xy.first
        height = xy.second
    }
}