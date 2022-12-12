import java.util.*

fun main() {

    data class DistancePair(
        val location: Pair<Int,Int>,
        val distance: Int
    )

    class DistanceComparator : Comparator<DistancePair> {
        override fun compare(first:DistancePair, second: DistancePair): Int {
            return first.distance.compareTo(second.distance)
        }
    }

    fun nextNodes(matrix: MutableList<List<Int>>, node: Pair<Int,Int>): List<Pair<Int, Int>> {
        var nodes = arrayListOf<Pair<Int,Int>>()
        val x = node.first
        val y = node.second
        if (y > 0 && matrix[x][y-1] - matrix[x][y] <= 1)
            nodes.add(Pair(x, y-1))
        if (y < matrix[x].lastIndex && matrix[x][y+1] - matrix[x][y] <= 1)
            nodes.add(Pair(x, y+1))
        if (x > 0 && matrix[x-1][y] - matrix[x][y] <= 1)
            nodes.add(Pair(x-1, y))
        if (x < matrix.lastIndex && matrix[x+1][y] - matrix[x][y] <= 1)
            nodes.add(Pair(x+1, y))
        return nodes
    }

    fun dijkstra(start: Pair<Int, Int>, end: Pair<Int, Int>, matrix: MutableList<List<Int>>): Int {
        val queue = PriorityQueue(DistanceComparator())
        val visited = hashSetOf<Pair<Int,Int>>()
        queue.add(DistancePair(start, 1))

        while (queue.isNotEmpty()){
            val node = queue.poll()
            if (visited.contains(node.location)) continue

            visited.add(node.location)
            val neighbors = nextNodes(matrix, node.location)
            if (neighbors.any { it == end }) return node.distance
            neighbors.forEach{ adj ->
                if (!visited.contains(adj)) queue.add(DistancePair(adj, node.distance + 1))
            }
        }
        return -1
    }

    fun solve(input: List<String>): Int {
        var destination = Pair(0,0)
        var start = Pair(0,0)

        var matrix = mutableListOf<List<Int>>()
        input.forEachIndexed { index, chars ->
            val row = chars.mapIndexed { index2, char ->
                when (char) {
                    'S' -> {start = Pair(index, index2); 'a'.code - 97}
                    'E' -> {destination = Pair(index, index2); 'z'.code - 97}
                    else -> char.code - 97
                }
            }
            matrix.add(row)
        }

        var total = dijkstra(start, destination, matrix)
        println(total)

        // Part 2
        var startingNodes: MutableList<Pair<Int, Int>> = mutableListOf()
        matrix.forEachIndexed { x, row ->
            row.forEachIndexed { y, node ->
                if (node == 0) startingNodes.add(Pair(x,y))
            }
        }
        var partTwo = Int.MAX_VALUE
        startingNodes.forEach { node ->
            var total = dijkstra(node, destination, matrix)
            if (total != -1 && partTwo > total) partTwo = total
        }
        println("Part2: $partTwo")
        return total
    }

    var test = solve(readInput("Day12_test"))
    println("Test1: $test")
    check(test == 31)

    var first = solve(readInput("Day12_input"))
    println("Part1: $first")
}
