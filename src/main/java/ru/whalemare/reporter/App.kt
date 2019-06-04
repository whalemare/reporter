package ru.whalemare.reporter

import picocli.CommandLine
import ru.whalemare.reporter.cli.Code
import ru.whalemare.reporter.cli.FileController
import ru.whalemare.reporter.model.Dependency
import ru.whalemare.reporter.writer.ConsoleWriter
import ru.whalemare.reporter.writer.ExcelWriter
import ru.whalemare.reporter.writer.FileWriter
import ru.whalemare.reporter.writer.Writer
import java.io.File
import java.util.concurrent.Callable

/**
 * @since 2019
 * @author Anton Vlasov - whalemare
 */

@CommandLine.Command(
    name = "report",
    version = ["1.0"],
    mixinStandardHelpOptions = true,
    description = ["CLI tool for generate dependency description report"],
    exitCodeList = [
        """
            ${Code.SUCCESS}:Successful program execution
        """,
        """
            ${Code.USAGE_ERROR}:Usage error: user input for the command was incorrect
            e.g., the wrong number of arguments, a bad flag, a bad syntax in a parameter, etc.
        """,
        """
            ${Code.INTERNAL_ERROR}:Internal software error: an exception occurred when invoking the business logic of this command.
        """
    ]
)
class App : Callable<Int> {

    @CommandLine.Option(
        names = ["--file", "-f"],
        required = true,
        description = ["Path to build.gradle file for automatically scan dependencies"]
    )
    var file: File? = null

    @CommandLine.Option(
        names = ["--excel"],
        description = ["Format your gradle dependencies to report.xslx Excel file"]
    )
    var excel: Boolean = true

    @CommandLine.Option(
        names = ["--json"],
        description = ["Format your gradle dependencies to report.json file"]
    )
    var json: Boolean = true

    @CommandLine.Option(
        names = ["--console"],
        description = ["Print dependencies report to console in .json format"]
    )
    var console: Boolean = true

    @CommandLine.Option(
        names = ["--repository", "-r"],
        description = []
    )
    var repo: String = "maven"

    override fun call(): Int {
        return if (file != null) {
            val raw = FileController(file!!).call()
            val writers = mutableListOf<Writer<Dependency>>()

            if (excel) writers.add(ExcelWriter())
            if (json) writers.add(FileWriter())
            if (console) writers.add(ConsoleWriter())

            if (repo != "maven") {
                throw UnsupportedOperationException("Supported only maven for now")
            }

            Reporter(
                raw,
                writers = writers
            ).run()
            Code.SUCCESS
        } else {
            Code.FILE_NOT_EXISTS
        }
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            System.exit(CommandLine(App()).execute(*args))
        }
    }
}
