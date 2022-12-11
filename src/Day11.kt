data class Monkey(val items: MutableList<Long>,
                  val operation: (Long) -> Long,
                  val testValue: Int,
                  val ifTrue: Int,
                  val ifFalse: Int,) {
    var inspected: Long = 0

    // Pair<ToMonkey, newWorryLevel>
    private fun test(manageWorryLevel: (Long) -> Long, value: Long): Pair<Int, Long> {
        val newWorryLevel = manageWorryLevel(value)
        return if (newWorryLevel % testValue == 0L)
            Pair(ifTrue, newWorryLevel) else Pair(ifFalse, newWorryLevel)
    }

    fun testItems(manageWorryLevel: (Long) -> Long): MutableList<Pair<Int, Long>> {
        var results = mutableListOf<Pair<Int , Long>>()
        items.forEach{item ->
            val worryLevelInspection = this.operation(item)
            val toMonkeyNewWorryLevel = this.test(manageWorryLevel, worryLevelInspection)
            results.add(toMonkeyNewWorryLevel)
            inspected++
        }
        items.clear()
        return results
    }
}

fun main() {
    fun operation(operationValue: String): (Long) -> Long {
        return when {
            operationValue == "* old" -> { first: Long -> first * first }
            operationValue.startsWith("* ") -> {
                first: Long -> first * operationValue.split("* ")[1].toLong()
            }
            operationValue.startsWith("+ ") -> {
                first: Long -> first + operationValue.split("+ ")[1].toLong()
            }
            else -> {throw IllegalArgumentException("parsing operations")}
        }
    }

    fun solve(input: List<String>, rounds: Int): Long {
        var monkeys = mutableListOf<Monkey>()

        input.windowed(7, step = 7, partialWindows = true).forEach { line ->
            val monkey = Monkey(
                line[1].substringAfter(": ").split(", ")
                    .map { it.toLong() }.toMutableList(),
                operation(line[2].split("  Operation: new = old ")[1]),
                line[3].split("Test: divisible by ")[1].toInt(),
                line[4].split("If true: throw to monkey ")[1].toInt(),
                line[5].split("If false: throw to monkey ")[1].toInt()
            )
            monkeys.add(monkey)
        }

        var manageWorryLevel: (Long) -> Long = if (rounds == 20) {
            { worryLevel: Long -> worryLevel / 3 }
        } else {
            val commonDivisor = monkeys.map { it.testValue }.reduce { acc, i -> acc * i }
            ({ worryLevel: Long -> worryLevel % commonDivisor })
        }

        repeat(rounds) {
            monkeys.forEach { monkey ->
                monkey.testItems(manageWorryLevel).forEach { result ->
                    monkeys[result.first]?.items?.add(result.second)
                }
            }
        }

        return monkeys.map { it.inspected }.sortedDescending().subList(0, 2)
            .reduce { acc, i -> acc * i }
    }

    var test = solve(readInput("Day11_test"), 20)
    println("Test1: $test")
    check(test == 10605L)
    test = solve(readInput("Day11_test"), 10000)
    println("Test2: $test")

    val first = solve(readInput("Day11_input"), 20)
    println("Part1: $first")
    val second = solve(readInput("Day11_input"), 10000)
    println("Part2: $second")
}
