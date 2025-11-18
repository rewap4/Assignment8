package org.example

import kotlin.random.Random
import kotlin.time.Duration
import kotlin.time.TimeSource
import kotlin.time.measureTime
/**
 * this runs a benchmark comparing the kd-tree and brute-force nearest neighbor algorithms.
 *
 * It generates a random training set in the k dimensional space,
 * and 1000 random query points.
 *
 * @param k number of coordinates for each point. must be one or more
 * @param numPoints number of training points. must be one or more
 * @return the KD-tree build time,KD-tree total time for 1000 queries, and
 * the brute-force total query time for 1000 queries
 */
fun runExperiment(k: Int, numPoints: Int): Triple<Duration, Duration, Duration> {
    require(k > 0)
    require(numPoints > 0)

    val rng = Random.Default
    fun randomPoints(n: Int, dim: Int) =
        List(n) { DoubleArray(dim) { rng.nextDouble(-1000.0, 1000.0) } }

    val train = randomPoints(numPoints, k)
    val queries = randomPoints(1000, k)

    val mark = TimeSource.Monotonic.markNow()
    val tree = KDTree(train)
    val buildDur = mark.elapsedNow()

    val kdDur = measureTime { for (q in queries) tree.nearest(q) }
    val bfDur = measureTime { for (q in queries) bruteForceNN(train, q) }

    return Triple(buildDur, kdDur, bfDur)
}