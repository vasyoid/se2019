package net.netau.vasyoid.command

import net.netau.vasyoid.VariablesStorage
import net.netau.vasyoid.exception.CommandException
import org.junit.Assert.*
import org.junit.Test

class AssignTest {

    private val input = System.`in`.bufferedReader()
    private val output = System.`out`.bufferedWriter()

    @Test(expected = CommandException::class)
    fun tooManyArguments() {
        val arguments = listOf("a", "b", "c")
        Assign(input, arguments, output, VariablesStorage()).run()
    }

    @Test(expected = CommandException::class)
    fun tooFewArguments() {
        val arguments = listOf("a")
        Assign(input, arguments, output, VariablesStorage()).run()
    }

    @Test
    fun correctAssignment() {
        val storage = VariablesStorage()
        val arguments = listOf("a", "asd af")
        assertTrue(Assign(input, arguments, output, storage).run())
        assertEquals("asd af", storage.get("a"))
    }
}