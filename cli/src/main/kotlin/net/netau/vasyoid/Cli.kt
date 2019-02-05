package net.netau.vasyoid

import net.netau.vasyoid.parser.QuotesExpander
import net.netau.vasyoid.parser.VariablesSubstitutor
import net.netau.vasyoid.parser.WordsSplitter

object Cli {

    private fun processInput(input: String) {
        var tokens = WordsSplitter.split(input)
        tokens = QuotesExpander.expand(tokens)
        tokens = VariablesSubstitutor.substitute(tokens)
        Interpreter.interpret(tokens)
        print("> ")
    }

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