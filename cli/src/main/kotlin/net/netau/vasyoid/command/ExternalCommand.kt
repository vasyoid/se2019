package net.netau.vasyoid.command

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.IOException

class ExternalCommand(
    stdin: BufferedReader,
    arguments: List<String>,
    stdout: BufferedWriter
) : Command(stdin, arguments, stdout) {

    override fun run(): Boolean {
        return try {
            val process = Runtime
                .getRuntime()
                .exec(arguments.joinToString(" "))
            writeInput(process)
            readOutput(process)
            readErrors(process)
            process.waitFor() == 0
        } catch (e : IOException) {
            System.err.println(e.message)
            false
        }
    }

    private fun writeInput(process: Process) {
        val processInput = process.outputStream.bufferedWriter()
        stdin.copyTo(processInput)
        processInput.close()
    }

    private fun readOutput(process: Process) {
        process.inputStream.bufferedReader().copyTo(stdout)
        stdout.flush()
    }

    private fun readErrors(process: Process) {
        process.errorStream.bufferedReader().forEachLine { line ->
            System.err.println(line)
        }
    }
}