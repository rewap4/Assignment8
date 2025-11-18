package org.example

fun main() {
    val dimensions = listOf(2, 4, 8, 16)
    val sizes = listOf(10, 100, 1000, 10000)

    println("k n buildKD KD(1000) BF(1000)")
    for (k in dimensions) {
        for (n in sizes) {
            val (buildDur, kdDur, bfDur) = runExperiment(k, n)
            println("$k $n $buildDur $kdDur $bfDur")
        }
    }
}

