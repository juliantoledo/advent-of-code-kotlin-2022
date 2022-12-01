fun main() {

    fun elfCalloriesList(input: List<String>): MutableList<Int> {
        var elfCalories: MutableList<Int> = mutableListOf()
        var indexElf = 0
        var sumCalories = 0
        for (i in input) {
            var calories = i.toIntOrNull()
            if (calories != null && calories > 0) {
                sumCalories += calories
            } else {
                elfCalories.add(sumCalories)
                sumCalories = 0
            }
        }
        return elfCalories
    }

    fun part1(elfCalories: List<Int>): Int {
        return elfCalories.max()
    }

    fun part2(elfCalories: MutableList<Int>): Int {
        elfCalories.sortDescending()
        return elfCalories[0] + elfCalories[1] + elfCalories[2]
    }

    val input = readInput("Day01_test")
    var elfCalories = elfCalloriesList(input)
    println( part1(elfCalories))
    println( part2(elfCalories))
}
