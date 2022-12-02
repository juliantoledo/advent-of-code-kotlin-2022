import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)


fun String.splitTo(s: String, predicate: (left: String, right: String) -> Unit) =
    split(s).pair(predicate)

fun <E> List<E>.pair(predicate: (left: E, right: E) -> Unit) =
    predicate(this[0], this[1])

