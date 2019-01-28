package net.netau.vasyoid

object VariablesStorage {

    private val variables = mutableMapOf<String, String>()

    fun get(variable: String): String {
        return variables.getOrDefault(variable, "")
    }

    fun set(variable: String, value: String) {
        return variables.set(variable, value)
    }
}