interface Packets {}
class PacketList(val packets: List<Packets>): Packets {}
class PacketItem(val packets: Int) : Packets {}

fun compare(p1: Packets, p2: Packets): Int {
    return when {
        p1 is PacketItem && p2 is PacketItem -> (p1.packets.compareTo(p2.packets))
        p1 is PacketList && p2 is PacketList -> compare(p1, p2)
        p1 is PacketList && p2 is PacketItem -> compare(p1, PacketList(listOf(p2)))
        p1 is PacketItem && p2 is PacketList -> compare(PacketList(listOf(p1)), p2)
        else -> throw Exception("Error comparing Packets")
    }
}

fun compare(p1: PacketList, p2: PacketList): Int {
    val pairs = p1.packets.zip(p2.packets)
    for ((pl1, pl2) in pairs) {
        val res = compare(pl1, pl2)
        if (res != 0) return res
    }
    return p1.packets.size.compareTo(p2.packets.size)
}

fun parse(string: String): Packets? {
    if (string.isEmpty()) return null

    if (string[0].isDigit()) return PacketItem(string.toInt())

    var bracketCount = 0
    var lastComma = 0
    val packets = mutableListOf<Packets?>()
    string.forEachIndexed { index, value ->
        if (value == '[') bracketCount++
        if (value == ']') {
            bracketCount--
            if (bracketCount == 0) packets += parse(string.take(index).drop(lastComma + 1))
        }
        if (value == ',') {
            if (bracketCount == 1) {
                packets += parse(string.take(index).drop(lastComma + 1))
                lastComma = index
            }
        }
    }
    return PacketList(packets.filterNotNull())
}

fun main() {
    fun solve(input: List<String>): Int {
        var packets: MutableList<Packets?> = input.filter { it.isNotEmpty() }.map { parse(it) } as MutableList<Packets?>

        // Part 1
        val total = packets.chunked(2).mapIndexed { index, pair ->
            if (compare(pair.first()!!, pair.last()!!) < 0) index + 1 else 0
        }.sum()

        // Part 2
        val divider1 = parse("[[2]]")
        val divider2 = parse("[[6]]")
        packets.add(divider1)
        packets.add(divider2)
        val sorted = packets.sortedWith { p1, p2 -> compare(p1!!, p2!!) }
        val second = (sorted.indexOf(divider1) + 1) * (sorted.indexOf(divider2) + 1)
        println("Part2: $second")
        return total
    }

    var test = solve(readInput("Day13_test"))
    println("Test1: $test")
    check(test == 13)

    val first = solve(readInput("Day13_input"))
    println("Part1: $first")
}
