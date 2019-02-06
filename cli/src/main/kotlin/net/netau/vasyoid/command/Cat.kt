package net.netau.vasyoid.command

import java.io.*


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
            System.err.println(e.message)
            false
        }
    }

    private fun cat(input: BufferedReader) {
        input.copyTo(stdout)
    }
}
