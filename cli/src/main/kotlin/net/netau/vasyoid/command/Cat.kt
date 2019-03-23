package net.netau.vasyoid.command

import net.netau.vasyoid.exception.CommandException
import java.io.*

/**
 * Cat command. Prints the contents of a file.
 */
class Cat(
    input: BufferedReader,
    arguments: List<String>,
    output: BufferedWriter
) : Command(input, arguments, output) {

    /**
     * @inheritDoc
     */
    override fun run(): Boolean {
        if (arguments.isEmpty()) {
            cat(input)
        }
        return try {
            arguments.forEach {
                cat(FileInputStream(File(it)).bufferedReader())
            }
            output.flush()
            true
        } catch (e: IOException) {
            throw CommandException("cat: " + e.message)
        }
    }

    private fun cat(input: BufferedReader) {
        input.copyTo(output)
    }
}
