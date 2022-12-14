fun main() {
    val start = Pair(500,0)
    var maxX = 0
    var maxY = 0
    var minX = Int.MAX_VALUE
    var minY = Int.MAX_VALUE
    val widerForPart = 1000

    fun printMatrix(matrix: MutableList<MutableList<String>>) {
        matrix.forEachIndexed{i, item ->
            println("$i ${item.subList(minX-200,maxX+200).joinToString(separator = "")}")
        }
    }

    fun sand(matrix: MutableList<MutableList<String>>, part2:Boolean = false): Int {
        var totalSand = 0
        val xRange = (minX until maxX)
        var sandFinal = false
        while(!sandFinal) {
            var sandX = start.first
            var sandY = start.second
            var sandRested = false
            while(!sandRested) {
                // Final for Part1/Endless
                if (!part2 && (sandX !in xRange || sandY >= maxY)) {
                    sandRested = true
                    sandFinal = true
                } else {
                    if (matrix[sandY + 1][sandX] == ".") { sandY++ }
                    else if (matrix[sandY + 1][sandX - 1] == ".") { sandY++; sandX-- }
                    else if (matrix[sandY + 1][sandX + 1] == ".") { sandY++; sandX++ }
                    else {
                        sandRested = true;
                        totalSand++
                        matrix[sandY][sandX] = "o"
                        // Final for Part2/Floor
                        if (part2 && sandY == start.second && sandX == start.first) { sandFinal = true }
                    }
                }
            }
        }
        return totalSand
    }

    fun solve(input: List<String>, part2:Boolean = false): Int {
        var paths: MutableList<List<Pair<Int,Int>>> = mutableListOf()
        input.forEach { line ->
            val path: List<Pair<Int,Int>> = line.split(" -> ").map {
                val a = it.split(",")
                Pair(a[0].toInt(), a[1].toInt())
            }
            path.forEach{point ->
                if (point.first > maxX) maxX = point.first
                if (point.second > maxY) maxY = point.second
                if (point.first < minX) minX = point.first
                if (point.second < minY) minY = point.second
            }
            paths.add(path)
        }

        var matrix = MutableList(maxY+3) { MutableList<String>(maxX+widerForPart) { "." }}
        matrix[start.second][start.first] = "+"

        paths.forEach{path ->
            path.forEachIndexed{index, point ->
                matrix[point.second][point.first] = "#"
                if (index < path.size-1) {
                    val postPoint = path[index+1]
                    if (point.first == postPoint.first) {
                        for (new in point.second + 1 until postPoint.second)
                            matrix[new][point.first] = "#"
                    }
                    if (point.second == postPoint.second) {
                        for (new in point.first + 1 until postPoint.first)
                            matrix[point.second][new] = "#"
                    }
                }
                if (index > 1) {
                    val prePoint = path[index-1]
                    if (point.first == prePoint.first) {
                        for (new in point.second + 1 until prePoint.second)
                            matrix[new][point.first] = "#"
                    }
                    if (point.second == prePoint.second) {
                        for (new in point.first + 1 until prePoint.first)
                            matrix[point.second][new] = "#"
                    }
                }
            }
        }

        // New Floor for Part2
        if (part2) for (i in 0 until maxX + widerForPart) matrix[maxY + 2][i] = "#"

        return sand(matrix, part2)
    }

    val test = solve(readInput("Day14_test"))
    println("Test1: $test")
    check(test == 24)
    println("Test2: " + solve(readInput("Day14_test"), true))
    println("Part1: " + solve(readInput("Day14_input")))
    println("Part2: " + solve(readInput("Day14_input"), true))
}
