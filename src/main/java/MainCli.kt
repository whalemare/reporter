import picocli.CommandLine
import java.io.File
import java.util.concurrent.Callable

/**
 * @since 2019
 * @author Anton Vlasov - whalemare
 */

fun main(args: Array<String>) {
//    System.exit(CommandLine(App()).execute(*args))
    System.exit(CommandLine(App()).execute(*args))
}

@CommandLine.Command(
    name = "report",
    version = ["1.0"],
    mixinStandardHelpOptions = true,
    description = ["CLI tool for generate dependency description report"]
)
class App : Callable<Int> {

    @CommandLine.Option(names = ["--file", "-f"])
    lateinit var file: File

    override fun call(): Int {
        println("Hello kotlin")
        println(file.path)
        return 0
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
//        @JvmStatic fun main(args: Array<String>) {
//            CommandLine(MyApp()).execute(*args)
//        }
//    }
//}
