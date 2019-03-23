package net.netau.vasyoid

/**
 * Global Variable storage. Stores the values bounded to variables.
 */
class VariablesStorage {

    private val variables = mutableMapOf<String, String>()

    /**
     * Gets the value af a variable or ""
     */
    fun get(variable: String): String {
        return variables.getOrDefault(variable, "")
    }

    /**
     * Binds a variable to a value
     */
    fun set(variable: String, value: String) {
        return variables.set(variable, value)
    }
}