package net.netau.vasyoid.command

import java.io.BufferedReader
import java.io.BufferedWriter

/**
 * The common ancestor for all standard commands
 */
abstract class Command(val stdin: BufferedReader, val arguments: List<String>, val stdout: BufferedWriter) {

    /**
     * main function of the command. Does all business logic.
     */
    abstract fun run(): Boolean
}
