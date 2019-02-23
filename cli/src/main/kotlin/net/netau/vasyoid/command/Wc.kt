package net.netau.vasyoid.command

import net.netau.vasyoid.exception.CommandException
import java.io.*
import kotlin.text.StringBuilder
import java.nio.charset.Charset

/**
 * Wc command. Prints the number of newlines, words and bytes in a file.
 */
class Wc(
    stdin: BufferedReader,
    arguments: List<String>,
    stdout: BufferedWriter
) : Command(stdin, arguments, stdout) {

    override fun run(): Boolean {
        if (arguments.isEmpty()) {
            printStat(wc())
            return true
        }
        val total = Statistics()
        arguments.forEach {
            val stat = wc(File(it))
            printStat(stat, it)
            total.lines += stat.lines
            total.words += stat.words
            total.bytes += stat.bytes
        }
        if (arguments.size > 1) {
            printStat(total, "total")
        }
        return true
    }

    private fun wc(file: File): Statistics {
        val stat = Statistics()
        var prevChar = ' '.toInt()
        try {
            val input = FileInputStream(file).reader(Charset.defaultCharset()).buffered()
            loop@ while (true) {
                val char = input.read()
                when {
                    char < 0 -> break@loop
                    char == '\n'.toInt() -> stat.lines++
                    !Character.isWhitespace(char) and Character.isWhitespace(prevChar) -> stat.words++
                }
                prevChar = char
            }
            stat.bytes = file.length()
        } catch (e: IOException) {
            throw CommandException("wc: " + e.message)
        }
        return stat
    }

    private fun wc(): Statistics {
        val stringBuilder = StringBuilder()
        loop@ while (true) {
            val char = stdin.read()
            if (char < 0) {
                break@loop
            }
            stringBuilder.append(char.toChar())
        }
        val stat = Statistics()
        val string = stringBuilder.toString()
        stat.bytes = string.toByteArray().size.toLong()
        stat.lines = string.count { it == '\n' }.toLong()
        stat.words = string.split(Regex("\\s")).size.toLong()
        return stat
    }

    private fun printStat(stat: Statistics, name: String = "") {
        stdout.write("${stat.lines} ${stat.words} ${stat.bytes} $name")
        stdout.newLine()
        stdout.flush()
    }

    companion object {

        private data class Statistics(var lines: Long = 0, var words: Long = 0, var bytes: Long = 0)
    }
}
