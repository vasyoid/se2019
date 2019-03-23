package net.netau.vasyoid.command

import org.junit.Assert.*
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File

class PwdTest {

    @Test
    fun pwd() {
        val arguments = listOf<String>()
        val outputStream = ByteArrayOutputStream()
        assertTrue(Pwd(System.`in`.bufferedReader(), arguments, outputStream.bufferedWriter()).run())
        val outputString = String(ByteArrayInputStream(outputStream.toByteArray()).readBytes())
        assertEquals(File("./").canonicalPath, outputString)
    }
}