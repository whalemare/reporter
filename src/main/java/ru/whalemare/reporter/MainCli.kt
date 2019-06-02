package ru.whalemare.reporter

import picocli.CommandLine
import ru.whalemare.reporter.cli.Code
import ru.whalemare.reporter.cli.FileController
import java.io.File
import java.util.concurrent.Callable

/**
 * @since 2019
 * @author Anton Vlasov - whalemare
 */

fun main(args: Array<String>) {
    System.exit(CommandLine(App()).execute(*args))
}

@CommandLine.Command(
    name = "report",
    version = ["1.0"],
    mixinStandardHelpOptions = true,
    description = ["CLI tool for generate dependency description report"],
    exitCodeList = [
        " 0:Successful program execution",
        """
            64:Usage error: user input for the command was incorrect
            e.g., the wrong number of arguments, a bad flag, a bad syntax in a parameter, etc.
        """,
        """
            70:Internal software error: an exception occurred when invoking the business logic of this command.
        """
    ]
)
class App : Callable<Int> {

    @CommandLine.Option(names = ["--file", "-f"])
    var file: File? = null

    override fun call(): Int {
        return if (file != null) {
            FileController(file!!).call()
        } else {
            return Code.FILE_NOT_EXISTS
        }
    }
}


//@CommandLine.Command(name = "MyApp", version = ["Kotlin picocli demo v1.0"],
//    mixinStandardHelpOptions = true,
//    description = ["@|bold Kotlin|@ @|underline picocli|@ example"])
//class MyApp : Runnable {
//
//    @CommandLine.Option(names = ["-c", "--count"], paramLabel = "COUNT",
//        description = ["the count"])
//    private var count: Int = 0
//
//    override fun run() {
//        for (i in 0 until count) {
//            println("hello world $i...")
//        }
//    }
//    companion object {
//        @JvmStatic fun ru.whalemare.reporter.main(args: Array<String>) {
//            CommandLine(MyApp()).execute(*args)
//        }
//    }
//}
