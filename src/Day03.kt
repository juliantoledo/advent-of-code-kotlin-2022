fun main() {
/*
A for Rock, B for Paper, and C for Scissors
X for Rock, Y for Paper, and Z for Scissors

1 for Rock, 2 for Paper, and 3 for Scissors) plus the score for the outcome of the round
(0 if you lost, 3 if the round was a draw, and 6 if you won).

X means you need to lose, Y means you need to end the round in a draw, and Z means you need to win.
 */

    fun itemsList(input: List<String>): List<Pair<List<Char>, List<Char>>> {
        var itemList = mutableListOf<Pair<List<Char>, List<Char>>>()
        input.forEach { line ->
            val charList = line.toList()
            val a = charList.subList(0, charList.size/2)
            val b = charList.subList(charList.size/2, charList.size)
            itemList.add(Pair(a, b))
        }
        return itemList
    }

    fun letterValue(char: Char): Int {
        var value = char.digitToInt(radix = 36) - 9
        if (char.isUpperCase()) value += 26
        return value
    }

    fun part1(itemList: List<Pair<List<Char>, List<Char>>> ): Int {
        var totalScore = 0
        itemList.forEach { items ->
            val common = items.first.intersect(items.second.toSet())
            val value = letterValue(common.first())
            totalScore += value
        }
        return totalScore
    }

    fun part2(input: List<String>): Int {
        var totalScore = 0
        var itemList = mutableListOf<List<Char>>()
        input.forEach { line ->
            val charList = line.toList()
            itemList.add(charList)
        }
        for (i in 0 until itemList.size step 3 ) {
            val common = itemList[i].intersect(itemList[i+1]).intersect(itemList[i+2])
            val value = letterValue(common.first())
            totalScore += value
        }
        return totalScore
    }

    fun part2(elfCalories: MutableList<Int>): Int {
        elfCalories.sortDescending()
        return elfCalories[0] + elfCalories[1] + elfCalories[2]
    }

    var input = readInput("Day03_test")
    var items = itemsList(input)
    println( part1(items))

    input = readInput("Day03_input")
    items = itemsList(input)
    println( part1(items))

    println( part2(input))
}
