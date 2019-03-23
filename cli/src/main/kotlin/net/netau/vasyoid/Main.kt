package net.netau.vasyoid

/**
 * Main cli loop.
 */
fun main() {
    val cli = Cli(Parser(), Interpreter(), VariablesStorage())
    print("> ")
    System.`in`.bufferedReader().useLines { lines ->
        lines.forEach { line ->
            cli.processInput(line)
        }
    }
}
