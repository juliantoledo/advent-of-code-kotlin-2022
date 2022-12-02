fun main() {
/*
A for Rock, B for Paper, and C for Scissors
X for Rock, Y for Paper, and Z for Scissors

1 for Rock, 2 for Paper, and 3 for Scissors) plus the score for the outcome of the round
(0 if you lost, 3 if the round was a draw, and 6 if you won).

X means you need to lose, Y means you need to end the round in a draw, and Z means you need to win.
 */

    fun gamesList(input: List<String>): List<Pair<String, String>> {
        var games = mutableListOf<Pair<String, String>>()
        input.forEach { line ->
            line.splitTo(" ") { a, b ->
                games.add(Pair(a, b))
            }
        }
        return games
    }

    fun part1(games: List<Pair<String, String>>): Int {
        var totalScore = 0
        games.forEach { game ->
            var score = 0
            when (game.first) {
                "A" -> when (game.second) {
                    "X" -> score += 3
                    "Y" -> score += 6
                    "Z" -> score += 0
                }
                "B" -> when (game.second) {
                    "X" -> score += 0
                    "Y" -> score += 3
                    "Z" -> score += 6
                }
                "C" -> when (game.second) {
                    "X" -> score += 6
                    "Y" -> score += 0
                    "Z" -> score += 3
                }
            }
            when (game.second) {
                "X" -> score += 1
                "Y" -> score += 2
                "Z" -> score += 3
            }
            //println("$game $score")
            totalScore += score
        }
        return totalScore
    }

    fun part2(games: List<Pair<String, String>>): List<Pair<String, String>> {
        var newGames = mutableListOf<Pair<String, String>>()
        games.forEach { game ->
            var newSecond = game.second
            when (game.first) {
               // A for Rock, B for Paper, and C for Scissors
                //    X for Rock, Y for Paper, and Z for Scissors
                // X means you need to lose, Y means you need to end the round in a draw, and Z means you need to win.
                "A" -> when (game.second) {
                    "X" -> newSecond = "Z"
                    "Y" -> newSecond = "X"
                    "Z" -> newSecond = "Y"
                }
                "C" -> when (game.second) {
                    "X" -> newSecond = "Y"
                    "Y" -> newSecond = "Z"
                    "Z" -> newSecond = "X"
                }
            }
            newGames.add(Pair(game.first, newSecond))
        }
        return newGames
    }

    fun part2(elfCalories: MutableList<Int>): Int {
        elfCalories.sortDescending()
        return elfCalories[0] + elfCalories[1] + elfCalories[2]
    }

    var input = readInput("Day02_test")
    var games = gamesList(input)
    println( part1(games))

    input = readInput("Day02_input")
    games = gamesList(input)
    println( part1(games))

    input = readInput("Day02_test")
    games = gamesList(input)
    println( part1(part2(games)))

    input = readInput("Day02_input")
    games = gamesList(input)
    println( part1(part2(games)))
}
