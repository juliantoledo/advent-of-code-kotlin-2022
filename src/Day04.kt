fun main() {

    fun pairsList(input: List<String>): List<Pair<Pair<Int,Int>, Pair<Int,Int>>> {
        var pairs = mutableListOf<Pair<Pair<Int,Int>, Pair<Int,Int>>>()
        input.forEach { line ->
            line.splitTo(",") { a, b ->
                a.splitTo("-") { i, j ->
                    b.splitTo("-") { k, l ->
                        pairs.add(
                            Pair(
                                Pair(i.toInt(), j.toInt()),
                                Pair(k.toInt(), l.toInt())
                            )
                        )
                    }
                }
            }
        }
        return pairs
    }

    fun part1(pairsList: List<Pair<Pair<Int,Int>, Pair<Int,Int>>>): Int {
        var totalPairs = 0
        pairsList.forEach { pair->
            if (pair.first.first >= pair.second.first) {
                if (pair.first.second <= pair.second.second) {
                    totalPairs += 1
                    return@forEach
                }
            }
            if (pair.second.first >= pair.first.first) {
                if (pair.second.second <= pair.first.second) totalPairs += 1
            }
        }
        return totalPairs
    }

    fun part2(pairsList: List<Pair<Pair<Int,Int>, Pair<Int,Int>>>): Int {
        var totalPairs = 0
        pairsList.forEach { pair->
            if (pair.second.first >= pair.first.first &&
                pair.second.first <= pair.first.second) {
                totalPairs += 1
                return@forEach
            }
            if (pair.first.first >= pair.second.first &&
                pair.first.first <= pair.second.second) {
                totalPairs += 1
            }
        }
        return totalPairs
    }

    var input = readInput("Day04_test")
    var items = pairsList(input)
    println(part1(items))

    input = readInput("Day04_input")
    items = pairsList(input)
    println( part1(items))

    println( part2(items))
}
