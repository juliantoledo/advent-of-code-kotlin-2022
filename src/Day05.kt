fun main() {
    fun processInput(input: List<String>, columns: Int): Pair<Array<MutableList<Char>>, MutableList<Triple<Int,Int, Int>>> {
        var stacks = Array<MutableList<Char>>(columns){mutableListOf<Char>()}
        var moves = mutableListOf<Triple<Int,Int, Int>>()
        input.forEach { line ->
            if (line.startsWith(" ") or line.startsWith("[")) {
                for (i in 0..columns) {
                    var index = if (i==0) 1 else i*4+1
                    if (line.length > index) {
                        var letter = line[index]
                        if (letter.isLetter()) {
                            stacks[i].add(letter)
                        }
                    }
                }
            }
            if (line.startsWith("m")) {
                var temp = line.split("move ", " from ", " to ")
                moves.add(Triple(temp[1].toInt(), temp[2].toInt(), temp[3].toInt()))
            }
        }
        //println(stacks.toList())
        //println(moves.toList())
        return Pair(stacks, moves)
    }

    fun part1(stacks: Array<MutableList<Char>>, moves:MutableList<Triple<Int,Int, Int>>): String {
        var topString = ""
        moves.forEach { move ->
            for (i in 1..move.first) {
                stacks[move.third - 1].add(0, stacks[move.second - 1].removeFirst())
            }
        }
        stacks.forEach { top ->
            topString += top.first()
        }
        return topString
    }

    fun part2(stacks: Array<MutableList<Char>>, moves:MutableList<Triple<Int,Int, Int>>): String {
        var topString = ""
        moves.forEach { move ->
            var temp = (mutableListOf <Char>())
            for (i in 1..move.first) {
                temp.add (stacks[move.second - 1].removeFirst())
            }
            stacks[move.third - 1].addAll(0, temp)
        }
        stacks.forEach { top ->
            topString += top.first()
        }
        return topString
    }

    var input = readInput("Day05_test")
    var items = processInput(input, 3)
    var stacks = items.first
    var moves = items.second
    println(stacks.toList())
    println(part1(stacks, moves))

    input = readInput("Day05_test")
    items = processInput(input, 3)
    stacks = items.first
    moves = items.second
    println(part2(stacks, moves))

    input = readInput("Day05_input")
    items = processInput(input, 9)
    stacks = items.first
    moves = items.second
    println(part1(stacks, moves))

    input = readInput("Day05_input")
    items = processInput(input, 9)
    stacks = items.first
    moves = items.second
    println(part2(stacks, moves))
}
