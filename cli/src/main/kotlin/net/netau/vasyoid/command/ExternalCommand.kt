package net.netau.vasyoid.command

import net.netau.vasyoid.exception.CommandException
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.IOException

/**
 * Execute external command command. Called when a command is unknown.
 */
class ExternalCommand(
    input: BufferedReader,
    arguments: List<String>,
    output: BufferedWriter
) : Command(input, arguments, output) {

    override fun run(): Boolean {
        val prefix = if (System.getProperty("os.name").startsWith("Win")) "cmd /c " else ""
        return try {
            val process = Runtime
                .getRuntime()
                .exec(prefix + arguments.joinToString(" "))
            writeInput(process)
            readOutput(process)
            readErrors(process)
            process.waitFor() == 0
        } catch (e : IOException) {
            throw CommandException(arguments[0] + e.message)
        }
    }

    private fun writeInput(process: Process) {
        val processInput = process.outputStream.bufferedWriter()
        input.copyTo(processInput)
        processInput.close()
    }

    private fun readOutput(process: Process) {
        process.inputStream.bufferedReader().copyTo(output)
        output.flush()
    }

    private fun readErrors(process: Process) {
        process.errorStream.bufferedReader().forEachLine { line ->
            System.err.println(line)
        }
    }
}