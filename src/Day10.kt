fun main() {

    fun solve(input: List<String>): Int {
        var cycle = 0
        var register = 1
        var strengthSum = 0

        var matrix = MutableList<String>(240) { "." }
        fun addCycle() {
            if (cycle <=240) {
                if (cycle % 40 in (register - 1..register + 1)) {
                    matrix[cycle] = "#"
                }
            }
            cycle++
            if (cycle in listOf<Int>(20, 60, 100, 140, 180, 220)) {
                strengthSum += cycle * register
            }
        }

        input.forEach { line ->
            val command = line.split(" ")
            when (command[0]) {
                "noop" -> {
                    addCycle()
                }
                "addx" -> {
                    repeat(2) {
                        addCycle()
                    }
                    register += command[1].toInt()
                }
            }
        }

        matrix.chunked(40).forEach{ item ->
            println(item.joinToString(separator = ""))
        }

        return strengthSum
    }

    println("Test:")
    val test = solve(readInput("Day10_test"))
    check(test == 13140)
    println("Part1: $test")
    println("")
    println("Part1: " + solve(readInput("Day10_input")))
}
