package ru.whalemare.reporter.cli

import ru.whalemare.reporter.Reporter
import java.io.File
import java.util.concurrent.Callable

/**
 * @since 2019
 * @author Anton Vlasov - whalemare
 */
class FileController(private val file: File) : Callable<Int> {
    override fun call(): Int {
        return if (file.exists()) {
            if (file.isDirectory) {
                processDirectory(file)
            } else {
                processFile(file)
            }
        } else {
            Code.FILE_NOT_EXISTS
        }
    }

    private fun processDirectory(file: File): Int {
        val gradle = file.listFiles { dir, name -> name == "build.gradle" }.first()
        return processFile(gradle)
    }

    private fun processFile(file: File): Int {
        val lines = file.readLines().map(String::trim)
        val dependencies = extractBlockCode(lines, "dependencies")
        Reporter(dependencies).run()
        return Code.SUCCESS
    }

    private fun extractBlockCode(lines: List<String>, blockName: String): List<String> {
        var startBlockIndex = -1
        var endBlockIndex = -1
        var countOpenBrackets = 0

        lines.forEachIndexed { index, line ->
            if (line.contains(blockName) && line.endsWith("{")) {
                startBlockIndex = index
            }
        }

        val linesTopDeps = lines.subList(startBlockIndex, lines.size - 1)
        var index = 0;
        for (line in linesTopDeps) {
            index++;
            if (line.endsWith("{")) {
                countOpenBrackets++
            }

            if (line.endsWith("}")) {
                countOpenBrackets--
            }

            if (countOpenBrackets == 0) {
                endBlockIndex = index
                break
            }
        }
        return linesTopDeps.subList(1, endBlockIndex-1)
    }


}
