package net.netau.vasyoid.command

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File

/**
 * Pwd command. Prints the current path.
 */
class Pwd(
    input: BufferedReader,
    arguments: List<String>,
    output: BufferedWriter
) : Command(input, arguments, output) {

    override fun run(): Boolean {
        output.write(File("./").canonicalPath)
        output.flush()
        return true
    }
}
