package net.netau.vasyoid.command

import java.io.BufferedReader
import java.io.BufferedWriter

abstract class Command(val stdin: BufferedReader, val arguments: List<String>, val stdout: BufferedWriter) {

    abstract fun run(): Boolean
}
