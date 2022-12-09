import kotlin.math.abs

fun main() {
    data class Point(var x: Int, var y: Int)

    fun printMatrix(points: Set<Point>) {
        val maxX = points.maxBy{point -> point.x}
        val minX = points.minBy{point -> point.x}
        val maxY = points.maxBy{point -> point.y}
        val minY = points.minBy{point -> point.y}
        val newPoints:  List<Point> = points.map { point -> Point(point.x + abs(minX.x), point.y + abs(minY.y)) }
        val width = maxX.x - minX.x + 1
        val height = maxY.y - minY.y + 1
        var matrix = MutableList(height) { MutableList<String>(width) { "." }}
        matrix[newPoints.first().y][newPoints.first().x] = "s"
        newPoints.drop(1).forEach { point ->
            matrix[point.y][point.x] = "#"
        }
        matrix.forEach{ item ->
            println(item.joinToString(separator = ""))
        }
        println()
    }

    fun near(head: Point, tail: Point): Boolean {
        return (head.x - tail.x) in (-1..1).toList() && (head.y - tail.y) in (-1..1).toList()
    }

    fun solve(input: List<String>, knots: Int ): Int {
        val points = (1..knots).map { Point(0, 0) }
        val head = points.first()
        val tail = points[1]

        val visited = mutableSetOf<Point>()
        visited.add(tail.copy())

        input.forEach { line ->
            line.splitTo(" ") { dir, moves ->
                repeat(moves.toInt()) {
                    when (dir) {
                        "L" -> { head.x = head.x - 1 }
                        "R" -> { head.x = head.x + 1 }
                        "U" -> { head.y = head.y - 1 }
                        "D" -> { head.y = head.y + 1 }
                    }
                    for (i in 0 until points.size-1) {
                        if (!near(points[i], points[i+1])) {
                            points[i+1].x += Integer.signum(points[i].x - points[i+1].x)
                            points[i+1].y += Integer.signum(points[i].y - points[i+1].y)
                        }
                    }
                    visited.add(points.last().copy())
                }
            }
        }
        printMatrix(visited)
        return visited.size
    }

    check(solve(readInput("Day09_test"), 2) == 13)
    println("Part1: " + solve(readInput("Day09_input"), 2))
    check(solve(readInput("Day09_test2"), 10) == 36)
    println("Part2: " + solve(readInput("Day09_input"), 10))
}
