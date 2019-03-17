package net.netau.vasyoid

import net.netau.vasyoid.exception.CommandException


/**
 * Main object. Performs the Cli user interaction.
 */
class Cli(
    private val parser: Parser,
    private val interpreter: Interpreter,
    private val storage: VariablesStorage
) {

    /**
     * Parse and execute a command.
     */
    fun processInput(input: String) {
        try {
            val tokens = parser.parse(input, storage)
            interpreter.interpret(tokens, storage)
        } catch (e: RuntimeException) {
            System.err.println(e.message)
        }
        print("> ")
    }
}