package net.netau.vasyoid.command

import joptsimple.OptionException
import joptsimple.OptionParser
import joptsimple.OptionSet
import net.netau.vasyoid.exception.CommandException
import java.io.*

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
    private var grepInput = input

    /**
     * @inheritDoc
     */
    override fun run(): Boolean {
        parseArguments()
        var linesToPrint = 0
        while (true) {
            val line = grepInput.readLine() ?: break
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

    private fun parseArguments() {
        val parser = OptionParser()
        parser.accepts("i", "ignore case")
        parser.accepts("w", "look for full words")
        parser.accepts("A", "print n lines after match")
            .withRequiredArg().describedAs("n").ofType(Integer::class.java)
        lateinit var options: OptionSet
        try {
            options = parser.parse(*arguments.toTypedArray())
        } catch (e: OptionException) {
            fail(e.message ?: "", parser)
        }
        if (options.has("A")) {
            lines = options.valueOf("A") as Int
            if (lines < 0) {
                fail("context length must be non-negative", parser)
            }
        }
        lines++
        val args = options.nonOptionArguments()
        when {
            args.size < 1 -> fail("Specify exactly one pattern", parser)
            args.size > 2 -> fail("Too many arguments", parser)
            args.size == 2 -> try {
                grepInput = FileInputStream(File(args[1] as String)).bufferedReader()
            } catch (e: IOException) {
                throw CommandException("grep: " + e.message)
            }
        }
        initRegex(options.has("i"), options.has("w"), options.nonOptionArguments()[0] as String)
    }

    private fun fail(message: String, parser: OptionParser) {
        val helpWriter = StringWriter()
        parser.printHelpOn(helpWriter)
        throw CommandException(message + "\n" + helpWriter.toString())
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