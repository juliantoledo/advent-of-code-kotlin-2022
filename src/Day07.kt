fun main() {

    class Dir(
        var name: String,
        var parent: Dir? = null,
        var dirs: MutableList<Dir> = mutableListOf(),
        var files: MutableMap<String, Int> = mutableMapOf()
    ){
        fun allDirs(): List<Dir> = dirs + dirs.flatMap { it.allDirs() }
        fun size(): Int = files.values.sum() + dirs.sumOf { it.size() }
    }

    fun process(input: List<String>): Dir {
        val top = Dir("/")
        var current = top
        input.drop(1).forEach { line ->
            // println(line)
            when {
                line.startsWith("$ cd ..") -> current = current.parent!!
                line.startsWith("$ cd") ->
                    current = current.dirs.first { it.name == line.substringAfter("cd ") }
                line.startsWith("dir") ->
                    current.dirs.add(Dir(line.substringAfter("dir "), current))
                !line.contains("$") -> {
                    val size = line.split(" ")[0].toInt()
                    val name = line.split(" ")[1]
                    current.files.getOrPut(name) { size }
                }
            }
        }
        return top
    }

    fun part1(top: Dir) {
        println("Part1: " + top.allDirs().map { it.size() }.filter { it < 100_000 }.sum())
    }

    fun part2(top: Dir) {
        val toFree = 30000000 - (70000000-top.size())
        println("Part2: " + top.allDirs().map { it.size() }.sorted().first { it >= toFree })
    }

    var input = readInput("Day07_test")
    var top = process(input)
    part1(top)
    part2(top)

    input = readInput("Day07_input")
    top = process(input)
    part1(top)
    part2(top)
}