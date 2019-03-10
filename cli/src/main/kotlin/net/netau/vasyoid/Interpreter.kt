package net.netau.vasyoid

import net.netau.vasyoid.command.*
import net.netau.vasyoid.exception.CommandException
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

/**
 * Interpreter. Interprets parsed user commands.
 */
class Interpreter {

    /**
     * Interprets parsed user commands.
     */
    fun interpret(command: List<String>, storage: VariablesStorage) {
        var input = ByteArrayInputStream(ByteArray(0)).bufferedReader()
        var mainToken = ""
        val arguments = mutableListOf<String>()
        var output: BufferedWriter

        command.forEach { token ->
            when (token) {
                "|" -> {
                    val outputStream = ByteArrayOutputStream()
                    output = outputStream.bufferedWriter()
                    if (!execute(input, mainToken, arguments as List<String>, output, storage)) {
                        return
                    }
                    input = ByteArrayInputStream(outputStream.toByteArray()).bufferedReader()
                    mainToken = ""
                    arguments.clear()
                }
                "=" -> {
                    if (mainToken == "" || arguments.isNotEmpty()) {
                        throw CommandException("Incorrect assignment command")
                    }
                    arguments += mainToken
                    mainToken = "="
                }
                else -> {
                    if (mainToken == "") {
                        mainToken = token
                    } else {
                        arguments += token
                    }
                }
            }
        }
        execute(input, mainToken, arguments, System.out.bufferedWriter(), storage)
    }

    private fun execute(
        input: BufferedReader,
        command: String,
        arguments: List<String>,
        output: BufferedWriter,
        storage: VariablesStorage
    ): Boolean {
        if (command == "") {
            throw CommandException("Empty command")
        }
        return when (command) {
            "=" -> Assign(input, arguments, output, storage).run()
            "cat" -> Cat(input, arguments, output).run()
            "echo" -> Echo(input, arguments, output).run()
            "wc" -> Wc(input, arguments, output).run()
            "pwd" -> Pwd(input, arguments, output).run()
            "exit" -> {
                System.exit(0)
                true
            }
            else -> ExternalCommand(input, listOf(command) + arguments, output).run()
        }
    }
}
