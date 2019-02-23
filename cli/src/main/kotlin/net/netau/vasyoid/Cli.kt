package net.netau.vasyoid

import net.netau.vasyoid.exception.CommandException


/**
 * Main object. Performs the Cli user interaction.
 */
object Cli {

    private val parser = Parser
    private val interpreter = Interpreter
    private val storage = VariablesStorage()

    private fun processInput(input: String) {
        val tokens = parser.parse(input, storage)
        try {
            interpreter.interpret(tokens, storage)
        } catch (e: CommandException) {
            System.err.println(e.message)
        }
        print("> ")
    }

    /**
     * Main cli loop.
     */
    @JvmStatic
    fun main(args: Array<String>) {

        print("> ")
        System.`in`.bufferedReader().useLines { lines ->
            lines.forEach { line ->
                processInput(line)
            }
        }
    }
}