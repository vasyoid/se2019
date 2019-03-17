package net.netau.vasyoid.command

import joptsimple.OptionException
import joptsimple.OptionParser
import java.io.BufferedReader
import java.io.BufferedWriter
import java.lang.RuntimeException

/**
 * Grep command. Prints lines that contain a specified pattern.
 */
class Grep(
    input: BufferedReader,
    arguments: List<String>,
    output: BufferedWriter
) : Command(input, arguments, output) {

    private var lines = 0
    private lateinit var regex: Regex

    /**
     * @inheritDoc
     */
    override fun run(): Boolean {
        if (!parseArguments()) {
            return false
        }
        var linesToPrint = 0
        while (true) {
            val line = input.readLine() ?: break
            if (line.contains(regex)) {
                linesToPrint = lines
            }
            if (linesToPrint > 0) {
                output.write(line)
                output.newLine()
                linesToPrint--
            }
        }
        output.flush()
        return true
    }

    private fun parseArguments(): Boolean {
        val parser = OptionParser()
        parser.accepts("i", "ignore case")
        parser.accepts("w", "look for full words")
        parser.accepts("A", "print n lines after match")
            .withRequiredArg().describedAs("n").ofType(Integer::class.java)
        try {
            val options = parser.parse(*arguments.toTypedArray())
            if (options.has("A")) {
                lines = options.valueOf("A") as Int
            }
            lines++
            initRegex(options.has("i"), options.has("w"), options.nonOptionArguments().single() as String)
        } catch (e: OptionException) {
            System.err.println(e.message + "\n")
            parser.printHelpOn(System.out)
            return false
        } catch (e: RuntimeException) {
            System.err.println("Specify exactly one pattern\n")
            parser.printHelpOn(System.out)
            return false
        }
        return true
    }

    private fun initRegex(ignoreCase: Boolean, words: Boolean, pattern: String) {
        val regexOptions = mutableSetOf<RegexOption>()
        if (ignoreCase) {
            regexOptions.add(RegexOption.IGNORE_CASE)
        }
        val regexPattern = if (words) "\\b$pattern\\b" else pattern
        regex = Regex(regexPattern, regexOptions)
    }
}