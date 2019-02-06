package net.netau.vasyoid.command

import net.netau.vasyoid.VariablesStorage
import java.io.BufferedReader
import java.io.BufferedWriter

class Assign(
    stdin: BufferedReader,
    arguments: List<String>,
    stdout: BufferedWriter
) : Command(stdin, arguments, stdout) {

    override fun run(): Boolean {
        if (arguments.size != 2) {
            System.err.println("Incorrect assignment command")
            return false
        }
        VariablesStorage.set(arguments[0], arguments[1])
        return true
    }
}