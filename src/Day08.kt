fun main() {
    fun isVisible(matrix: MutableList<Array<Int>>, x: Int, y: Int): Boolean {
        val value = matrix[x][y]
        if ( y == 0 || x == 0 || y == matrix[x].lastIndex || x == matrix.lastIndex) {
            return true
        } else {
            var left = true
            var right = true
            var up = true
            var down = true
            //left
            for (i in y-1 downTo 0) {
                if (value <= matrix[x][i]) {left = false; break}
            }
            // right
            for (i in y+1 .. matrix[x].lastIndex) {
                if (value <= matrix[x][i]) {right = false; break}
            }
            // up
            for (j in x-1 downTo 0) {
                if (value <= matrix[j][y]) {up = false; break}
            }
            // down
            for (j in x+1 .. matrix.lastIndex) {
                if (value <= matrix[j][y]) {down = false; break}
            }
            return left || right || up || down
        }
    }

    fun isVisibleDis(matrix: MutableList<Array<Int>>, x: Int, y: Int): Int {
        val value = matrix[x][y]
        if ( y == 0 || x == 0 || y == matrix[x].lastIndex || x == matrix.lastIndex) {
            return 0
        } else {
            var left = 1
            var right = 1
            var up = 1
            var down = 1
            //left
            for (i in y-1 downTo 1) {
                if (value <= matrix[x][i]) break else left++
            }
            // right
            for (i in y+1 until matrix[x].lastIndex) {
                if (value <= matrix[x][i]) break else right++
            }
            // up
            for (j in x-1 downTo 1) {
                if (value <= matrix[j][y]) break else up++
            }
            // down
            for (j in x+1 until matrix.lastIndex) {
                if (value <= matrix[j][y]) break else down++
            }
            return left * right * up * down
        }
    }

    fun findVisible(matrix: MutableList<Array<Int>>): List<Int> {
        var visiblePoints = mutableListOf<Int>()
        for((x, row) in matrix.withIndex()) {
            for((y, value) in row.withIndex()) {
                if (isVisible(matrix, x, y)) visiblePoints.add(value)
            }
        }
        return visiblePoints
    }

    fun findVisibleDis(matrix: MutableList<Array<Int>>): List<Int> {
        var visiblePoints = mutableListOf<Int>()
        for((x, row) in matrix.withIndex()) {
            for((y, value) in row.withIndex()) {
                visiblePoints.add(isVisibleDis(matrix, x, y))
            }
        }
        return visiblePoints
    }

    fun part1(input: List<String>): Int {
        var matrix = mutableListOf<Array<Int>>()
        for( i in input) {
            val row = i.map { it.digitToInt() }.toTypedArray()
            matrix.add(row)
        }
        return findVisible(matrix).size
    }

    fun part2(input: List<String>): Int {
        var matrix = mutableListOf<Array<Int>>()
        for( i in input) {
            val row = i.map { it.digitToInt() }.toTypedArray()
            matrix.add(row)
        }
        return findVisibleDis(matrix).maxOf { it }
    }

    var input = readInput("Day08_test")
    var x = part1(input)
    println(x)

    var y = part2(input)
    println(y)

    input = readInput("Day08_input")
    x = part1(input)
    println(x)

    y = part2(input)
    println(y)
}