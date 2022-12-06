fun main() {
    fun compareN (string: CharSequence, n: Int): Boolean {
        for (i in 0 until n) {
            for (j in i+1 until n) {
                if (string[i] == string[j]) return true
            }
        }
        return false
    }

    fun part1(input: List<String>): Int {
        input.forEach { line ->
            for (i in 0..line.length-4) {
                val sub = line.subSequence(i, i+4)
                if (!compareN(sub, 4)) {
                    val j = i+4
                    println("$sub $j")
                    return@forEach
                }
            }
        }
        return 0
    }

    fun part2(input: List<String>): Int {
        input.forEach { line ->
            for (i in 0..line.length-14) {
                val sub = line.subSequence(i, i+14)
                if (!compareN(sub, 14)) {
                    val j = i+14
                    println("$sub $j")
                    return@forEach
                }
            }
        }
        return 0
    }

    var input = readInput("Day06_test")
    part1(input)

    input = readInput("Day06_input")
    part1(input)

    part2(input)
}
