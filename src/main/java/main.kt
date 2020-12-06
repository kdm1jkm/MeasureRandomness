package com.github.kdm1jkm.measurerandomness.main

import java.io.FileInputStream
import java.util.*
import kotlin.math.abs
import kotlin.math.pow

fun main(args: Array<String>) {
    if (args[0] == "gen") {
        val random = Random()
        val builder = StringBuilder()
        for (i in 0..args[1].toInt()) {
            builder.append(random.nextInt(2))
        }
        println(builder.toString())
        return
    }

    val inputStream = FileInputStream(args[0])
    val scanner = Scanner(inputStream)
    val max = args[1].toInt()
    while (scanner.hasNextLine()) {
        val input = scanner.nextLine()
        val score = getScore(input, max)
        println("score = $score")
    }
}

fun getScore(input: String, max: Int): Double {
    var score = 0.0

    for (i in 0 until max) {
        val results = count(input, i + 1)

        val mid = input.length / 2.0.pow((i + 1).toDouble()).toInt()
        var localScore = 0
        for (num in results) {
            localScore += abs(mid - num)
        }
        val localMax = (input.length - i) + mid * (2.0.pow(i + 1) - 2)
        score += (localScore.toFloat() / localMax * 100)
    }
    score /= max.toDouble()
    score = 100 - score
    return score
}

fun count(string: String, length: Int): IntArray {
    val result = IntArray(2.0.pow(length.toDouble()).toInt())
    Arrays.fill(result, 0)
    for (i in 0 until string.length - length + 1) {
        result[string.substring(i, i + length).toInt(2)]++
    }
    return result
}
