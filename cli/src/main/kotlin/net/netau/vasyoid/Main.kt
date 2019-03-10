package net.netau.vasyoid

/**
 * Main cli loop.
 */
fun main() {

    print("> ")
    System.`in`.bufferedReader().useLines { lines ->
        lines.forEach { line ->
            Cli(Parser(), Interpreter(), VariablesStorage()).processInput(line)
        }
    }
}
