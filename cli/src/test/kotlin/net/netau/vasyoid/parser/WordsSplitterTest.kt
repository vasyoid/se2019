package net.netau.vasyoid.parser

import net.netau.vasyoid.exception.ParserException
import net.netau.vasyoid.Parser
import org.junit.Test

import org.junit.Assert.*

class WordsSplitterTest {

    @Test
    fun monolithicString() {
        val input = "helloWorld42"
        val output = listOf(input)
        assertEquals(output, Parser.splitWords(input))
    }

    @Test
    fun stringWithSpaces() {
        val input = "hello World 42"
        val output = listOf("hello", "World", "42")
        assertEquals(output, Parser.splitWords(input))
    }

    @Test
    fun stringWithPipes() {
        val input = "hello|World | 42"
        val output = listOf("hello", "|", "World", "|", "42")
        assertEquals(output, Parser.splitWords(input))
    }

    @Test
    fun stringWithAssignment() {
        val input = "hello=World"
        val output = listOf("hello", "=", "World")
        assertEquals(output, Parser.splitWords(input))
    }

    @Test
    fun stringWithSingleQuotes() {
        val input = "hello'w=o r|l\"d'"
        val output = listOf("hello", "'w=o r|l\"d'")
        assertEquals(output, Parser.splitWords(input))
    }

    @Test
    fun stringWithSingleQuotesAndSubstitution() {
        val input = "hello'\$world'"
        val output = listOf("hello", "'\$world'")
        assertEquals(output, Parser.splitWords(input))
    }

    @Test
    fun stringWithDoubleQuotes() {
        val input = "hello\"w=o r|l'd\""
        val output = listOf("hello", "\"w=o r|l'd\"")
        assertEquals(output, Parser.splitWords(input))
    }

    @Test
    fun stringWithDoubleQuotesAndSubstitution() {
        val input = "hello\"\$world\""
        val output = listOf("hello", "\"\$world\"")
        assertEquals(output, Parser.splitWords(input))
    }

    @Test(expected = ParserException::class)
    fun mismatchedSingleQuote() {
        val input = "echo text'text"
        Parser.splitWords(input)
    }

    @Test(expected = ParserException::class)
    fun mismatchedMultiQuote() {
        val input = "echo \"text"
        Parser.splitWords(input)
    }
}