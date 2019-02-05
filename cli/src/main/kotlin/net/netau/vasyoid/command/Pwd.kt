package net.netau.vasyoid.command

import net.netau.vasyoid.command.Command
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File

class Pwd(
    stdin: BufferedReader,
    arguments: List<String>,
    stdout: BufferedWriter
) : Command(stdin, arguments, stdout) {

    override fun run(): Boolean {
        stdout.write(File("./").canonicalPath)
        stdout.flush()
        return true
    }
}
