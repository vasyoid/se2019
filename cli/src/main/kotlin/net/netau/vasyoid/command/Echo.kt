package net.netau.vasyoid.command

import java.io.BufferedReader
import java.io.BufferedWriter

/**
 * Echo command. Prints its arguments.
 */
class Echo(
    input: BufferedReader,
    arguments: List<String>,
    output: BufferedWriter
) : Command(input, arguments, output) {

    override fun run(): Boolean {
        output.write(arguments.joinToString(" "))
        output.newLine()
        output.flush()
        return true
    }
}
