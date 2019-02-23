package net.netau.vasyoid.command

import net.netau.vasyoid.exception.CommandException
import java.io.*

/**
 * Cat command. Prints the contents of a file.
 */
class Cat(
    stdin: BufferedReader,
    arguments: List<String>,
    stdout: BufferedWriter
) : Command(stdin, arguments, stdout) {

    /**
     * @inheritDoc
     */
    override fun run(): Boolean {
        if (arguments.isEmpty()) {
            cat(stdin)
        }
        return try {
            arguments.forEach {
                cat(FileInputStream(File(it)).bufferedReader())
            }
            stdout.flush()
            true
        } catch (e: IOException) {
            throw CommandException("cat: " + e.message)
        }
    }

    private fun cat(input: BufferedReader) {
        input.copyTo(stdout)
    }
}
