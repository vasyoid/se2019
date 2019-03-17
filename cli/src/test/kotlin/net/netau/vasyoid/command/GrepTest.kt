package net.netau.vasyoid.command

import net.netau.vasyoid.exception.CommandException
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class GrepTest {

    @Rule
    @JvmField
    val tmpFolder = TemporaryFolder()

    private val inputString = "kasdk\nasd\nasd asd asd\nasdasd\nASD\nsdf\nsdf\nsdf\nasd ..\n"
    private val input = System.`in`.bufferedReader()
    private val output = System.out.bufferedWriter()
    private val fileName = "file"

    @Test
    fun normalGrep() {
        val expectedResult = "kasdk\nasd\nasd asd asd\nasdasd\nasd ..\n"
        val arguments = listOf("asd")
        checkSuccessful(expectedResult, arguments)
    }

    @Test
    fun grepFromFile() {
        val file = tmpFolder.newFile(fileName)
        file.writeText(inputString)
        val expectedResult = "kasdk\nasd\nasd asd asd\nasdasd\nasd ..\n"
        val arguments = listOf("asd", file.canonicalPath)
        checkSuccessful(expectedResult, arguments)
    }


    @Test
    fun grepWords() {
        val expectedResult = "asd\nasd asd asd\nasd ..\n"
        val arguments = listOf("-w", "asd")
        checkSuccessful(expectedResult, arguments)
    }

    @Test
    fun grepIgnoreCase() {
        val expectedResult = "kasdk\nasd\nasd asd asd\nasdasd\nASD\nasd ..\n"
        val arguments = listOf("-i", "asd")
        checkSuccessful(expectedResult, arguments)
    }

    @Test
    fun grepWordsIgnoreCase() {
        val expectedResult = "asd\nasd asd asd\nASD\nasd ..\n"
        val arguments = listOf("-wi", "asd")
        checkSuccessful(expectedResult, arguments)
    }

    @Test
    fun grepLines() {
        val expectedResult = "kasdk\nasd\nasd asd asd\nasdasd\nASD\nsdf\nasd ..\n"
        val arguments = listOf("-A", "2", "asd")
        checkSuccessful(expectedResult, arguments)
    }

    @Test
    fun grepLinesLinesWordsIgnoreCase() {
        val expectedResult = "asd\nasd asd asd\nasdasd\nASD\nsdf\nsdf\nasd ..\n"
        val arguments = listOf("-iwA", "2", "asd")
        checkSuccessful(expectedResult, arguments)
    }

    @Test
    fun grepRegex() {
        val expectedResult = "asd asd asd\nasd ..\n"
        val arguments = listOf("s\\w\\W")
        checkSuccessful(expectedResult, arguments)
    }

    @Test(expected = CommandException::class)
    fun grepInvalidOption() {
        val arguments = listOf("-s", "asd", "-i")
        Grep(input, arguments, output).run()
    }

    @Test(expected = CommandException::class)
    fun grepLinesNoParameter() {
        val arguments = listOf("asd", "-A")
        assertFalse(Grep(input, arguments, output).run())
    }

    @Test(expected = CommandException::class)
    fun grepLinesIncorrectParameter() {
        val arguments = listOf("-A", "asd")
        Grep(input, arguments, output).run()
    }

    @Test(expected = CommandException::class)
    fun grepNoPattern() {
        val arguments = listOf("-i")
        Grep(input, arguments, output).run()
    }

    @Test(expected = CommandException::class)
    fun grepMultiplePatterns() {
        val arguments = listOf("asd", "sdf", "dfg")
        Grep(input, arguments, output).run()
    }

    private fun checkSuccessful(expectedResult: String, arguments: List<String>) {
        val input = ByteArrayInputStream(inputString.toByteArray()).bufferedReader()
        val outputStream = ByteArrayOutputStream()
        assertTrue(Grep(input, arguments, outputStream.bufferedWriter()).run())
        val outputString = String(ByteArrayInputStream(outputStream.toByteArray()).readBytes())
        assertEquals(expectedResult.replace("\n", System.lineSeparator()), outputString)
    }
}
