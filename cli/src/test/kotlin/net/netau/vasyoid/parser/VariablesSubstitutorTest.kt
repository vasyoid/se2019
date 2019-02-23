package net.netau.vasyoid.parser

import net.netau.vasyoid.Parser
import net.netau.vasyoid.VariablesStorage
import org.junit.Assert.*
import org.junit.Test

class VariablesSubstitutorTest {

    @Test
    fun noSubstitution() {
        val storage = VariablesStorage()
        val input = listOf("hello")
        val output = listOf("hello")
        assertEquals(output, Parser.substituteVariables(input, storage))
    }

    @Test
    fun existingVariable() {
        val storage = VariablesStorage()
        storage.set("hello", "world")
        val input = listOf("\u0000hello world")
        val output = listOf("world world")
        assertEquals(output, Parser.substituteVariables(input, storage))
    }

    @Test
    fun variableWithDigits() {
        val storage = VariablesStorage()
        storage.set("he11o", "wor1d")
        val input = listOf("\u0000he11o")
        val output = listOf("wor1d")
        assertEquals(output, Parser.substituteVariables(input, storage))
    }

    @Test
    fun nonExistingVariable() {
        val storage = VariablesStorage()
        val input = listOf("hello|\u0000world~")
        val output = listOf("hello|~")
        assertEquals(output, Parser.substituteVariables(input, storage))
    }
}
