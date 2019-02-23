package net.netau.vasyoid.command

import net.netau.vasyoid.VariablesStorage
import net.netau.vasyoid.exception.CommandException
import org.junit.Assert.*
import org.junit.Test

class AssignTest {

    private val stdin = System.`in`.bufferedReader()
    private val stdout = System.`out`.bufferedWriter()

    @Test(expected = CommandException::class)
    fun tooManyArguments() {
        val arguments = listOf("a", "b", "c")
        Assign(stdin, arguments, stdout, VariablesStorage()).run()
    }

    @Test(expected = CommandException::class)
    fun tooFewArguments() {
        val arguments = listOf("a")
        Assign(stdin, arguments, stdout, VariablesStorage()).run()
    }

    @Test
    fun correctAssignment() {
        val storage = VariablesStorage()
        val arguments = listOf("a", "asd af")
        assertTrue(Assign(stdin, arguments, stdout, storage).run())
        assertEquals("asd af", storage.get("a"))
    }
}