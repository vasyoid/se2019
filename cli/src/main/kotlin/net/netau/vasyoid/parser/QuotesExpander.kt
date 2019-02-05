package net.netau.vasyoid.parser

object QuotesExpander {

    fun expand(input: List<String>): List<String> {
        return input.map {
            if (it[0] != '\'') {
                it.replace('$', '\u0000')
            } else {
                it
            }
        }
        .map {
            if (it[0] in "'\"") {
                it.substring(1, it.lastIndex)
            } else {
                it
            }
        }
    }
}
