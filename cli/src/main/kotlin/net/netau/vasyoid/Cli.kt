package net.netau.vasyoid

/**
 * Main object. Performs the Cli user interaction.
 */
object Cli {

    private val parser = Parser
    private val interpreter = Interpreter
    private val storage = VariablesStorage()

    private fun processInput(input: String) {
        try {
            val tokens = parser.parse(input, storage)
            interpreter.interpret(tokens, storage)
        } catch (e: RuntimeException) {
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