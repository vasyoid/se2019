package net.netau.vasyoid.command

import net.netau.vasyoid.VariablesStorage
import net.netau.vasyoid.exception.CommandException
import java.io.BufferedReader
import java.io.BufferedWriter

/**
 * Assignment command. Binds an environment variable to a value
 */
class Assign(
    stdin: BufferedReader,
    arguments: List<String>,
    stdout: BufferedWriter,
    private val storage: VariablesStorage
) : Command(stdin, arguments, stdout) {

    override fun run(): Boolean {
        if (arguments.size != 2) {
            throw CommandException("Incorrect assignment command")
        }
        storage.set(arguments[0], arguments[1])
        return true
    }
}