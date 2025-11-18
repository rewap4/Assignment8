package org.example

import kotlin.math.sqrt

private fun dist2(a: DoubleArray, b: DoubleArray): Double {
    var s = 0.0
    for (i in a.indices) {
        val d = a[i] - b[i]
        s += d * d
    }
    return s
}

class KDTree(points: List<DoubleArray>) {

    private data class Node(
        val point: DoubleArray,
        val axis: Int,
        val left: Node?,
        val right: Node?
    )

    private val k: Int
    private val root: Node

    init {
        require(points.isNotEmpty()) { "Empty points." }
        val oneDim = points.first().size
        require(oneDim > 0) { "Dimension must be > 0." }
        for (p in points) require(p.size == oneDim) { "All points must have the same dimension." }
        k = oneDim  // FIX: was `firstDim`
        val pts = points.map { it.copyOf() }.toMutableList()
        root = build(pts, 0)
    }

    fun nearest(query: DoubleArray): Pair<DoubleArray, Double> {
        require(query.size == k) { "Query dimension mismatch." }

        var bestPoint = root.point
        var bestD2 = dist2(query, bestPoint)

        fun search(n: Node?) {
            if (n == null) return

            val d2 = dist2(query, n.point)
            if (d2 < bestD2) {
                bestD2 = d2
                bestPoint = n.point
            }

            val axis = n.axis
            val qv = query[axis]
            val pv = n.point[axis]

            val near = if (qv < pv) n.left else n.right
            val far = if (qv < pv) n.right else n.left

            search(near)
            val delta = qv - pv
            if (delta * delta < bestD2) search(far)
        }

        search(root)
        return bestPoint to sqrt(bestD2)
    }

    private fun build(pts: MutableList<DoubleArray>, depth: Int): Node {
        val axis = depth % k
        pts.sortBy { it[axis] }
        val mid = pts.size / 2
        val left = if (mid > 0)
            build(pts.subList(0, mid).map { it.copyOf() }.toMutableList(), depth + 1)
        else null
        val right = if (mid + 1 < pts.size)
            build(pts.subList(mid + 1, pts.size).map { it.copyOf() }.toMutableList(), depth + 1)
        else null
        return Node(pts[mid].copyOf(), axis, left, right)
    }
}

fun bruteForceNN(points: List<DoubleArray>, query: DoubleArray): Pair<DoubleArray, Double> {
    require(points.isNotEmpty()) { "Empty points." }
    val k = points.first().size
    require(query.size == k) { "Query dimension mismatch." }

    var best = points[0]
    var bestD2 = dist2(query, best)
    for (i in 1 until points.size) {
        val p = points[i]
        val d2 = dist2(query, p)
        if (d2 < bestD2) {
            bestD2 = d2
            best = p
        }
    }
    return best to sqrt(bestD2)
}