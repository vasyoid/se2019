package net.netau.vasyoid.parser

import net.netau.vasyoid.VariablesStorage

object VariablesSubstitutor {

    fun substitute(input: List<String>): List<String> {
        return input.map {
            it.replace(Regex("\u0000\\w+")) { match -> VariablesStorage.get(match.value.drop(1)) }
        }
    }
}
