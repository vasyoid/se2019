package net.netau.vasyoid.command

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileInputStream


class Cat(
    stdin: BufferedReader,
    arguments: List<String>,
    stdout: BufferedWriter
) : Command(stdin, arguments, stdout) {

    override fun run(): Boolean {
        if (arguments.isEmpty()) {
            cat(stdin)
        }
        arguments.forEach {
            cat(FileInputStream(File(it)).bufferedReader())
        }
        stdout.flush()
        return true
    }

    private fun cat(input: BufferedReader) {
        input.copyTo(stdout)
    }
}
