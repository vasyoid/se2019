package net.netau.vasyoid.command

import java.io.BufferedReader
import java.io.BufferedWriter

/**
 * The common ancestor for all standard commands
 */
abstract class Command(val input: BufferedReader, val arguments: List<String>, val output: BufferedWriter) {

    /**
     * Main function of the command. Does all business logic.
     * Returns true on success and false otherwise.
     */
    abstract fun run(): Boolean
}
