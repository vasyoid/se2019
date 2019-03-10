package net.netau.vasyoid

import net.netau.vasyoid.exception.ParserException

/**
 * Cli parser. Gets a complex command and splits it into to simple piped ones.
 */
class Parser {

    private val replacement = "\u0000$1\u0000"
    private val singleQuot = "'[^']*'"
    private val doubleQuot = "\"[^\"]*\""
    private val noQuot = "[^'|\"]*"
    private val pipedCommand = "($noQuot($singleQuot|$doubleQuot)?$noQuot)*"
    private val validationRegex = Regex("$pipedCommand(\\|$pipedCommand)*")
    private val separatorsRegex = Regex("($singleQuot|$doubleQuot|\\||\\s+|=)")

    /**
     * Parses a command into tokens.
     */
    fun parse(input: String, storage: VariablesStorage): List<String> {
        var tokens = splitWords(input)
        tokens = expandQuotes(tokens)
        return substituteVariables(tokens, storage)
    }

    /**
     * Splits the input into tokens.
     */
    fun splitWords(input: String): List<String> {
        if (!input.matches(validationRegex)) {
            throw ParserException("Syntax error: mismatched quotes")
        }
        return input.replace(
            separatorsRegex,
            replacement
        )
            .split('\u0000')
            .filter { it.isNotBlank() }
    }

    /**
     * Analyses the quoting type and marks substitution points.
     */
    fun expandQuotes(input: List<String>): List<String> {
        return input.map {
            if (it[0] != '\'') {
                it.replace('$', '\u0000')
            } else {
                it
            }
        }.map {
            if (it[0] in "'\"") {
                it.substring(1, it.lastIndex)
            } else {
                it
            }
        }
    }

    /**
     * Substitutes variable values to substitutions points.
     */
    fun substituteVariables(input: List<String>, storage: VariablesStorage): List<String> {
        return input.map {
            it.replace(Regex("\u0000\\w+")) { match -> storage.get(match.value.drop(1)) }
        }
    }
}