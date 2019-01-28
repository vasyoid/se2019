package net.netau.vasyoid

object WordsSplitter {

    private val separatorsRegex = Regex("('[^']*'|\"[^\"]*\"|\\||\\s+|=)")
    private const val replacement = "\u0000$1\u0000"

    fun split(input: String): List<String> {
        return input.replace(separatorsRegex, replacement)
            .split('\u0000')
            .filter { it.isNotBlank() }
    }
}
