package ru.whalemare.reporter.writer

import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import ru.whalemare.reporter.model.Dependency
import java.io.FileOutputStream


/**
 * @since 2019
 * @author Anton Vlasov - whalemare
 */
class ExcelWriter : Writer<Dependency> {

    private val columns = listOf("Название", "Описание", "Версия", "Ссылка", "Лицензия", "Артефакт", "Дата публикации")

    override fun write(items: List<Dependency>) {
        val workbook = XSSFWorkbook() // new HSSFWorkbook() for generating `.xls` file

        /* CreationHelper helps us create instances of various things like DataFormat,
           Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
        val createHelper = workbook.creationHelper
        val sheet = workbook.createSheet("Зависимости")

        // Create a Font for styling header cells
        val headerFont = workbook.createFont()
        headerFont.bold = true
        headerFont.fontHeightInPoints = 14.toShort()
        headerFont.color = IndexedColors.BLACK.index

        // Create a CellStyle with the font
        val headerCellStyle = workbook.createCellStyle()
        headerCellStyle.setFont(headerFont)
        headerCellStyle.fillBackgroundColor = IndexedColors.GREY_25_PERCENT.index

        // Create a Row
        val headerRow = sheet.createRow(0)

        // Create cells
        for (i in 0 until columns.size) {
            val cell = headerRow.createCell(i)
            cell.setCellValue(columns[i])
            cell.cellStyle = headerCellStyle
            sheet.autoSizeColumn(i)
        }

        // Create Cell Style for formatting Date
        val dateCellStyle = workbook.createCellStyle()
        dateCellStyle.dataFormat = createHelper.createDataFormat().getFormat("d MMMM YYYY")

        items.forEachIndexed { index, dependency ->
            val row = sheet.createRow(index + 1)
            row.createCell(0).setCellValue(dependency.name)
            row.createCell(1).setCellValue(dependency.description)
            row.createCell(2).setCellValue(dependency.artifact.version)
            row.createCell(3).setCellValue(dependency.url)
            row.createCell(4).setCellValue(dependency.license)
            row.createCell(5).setCellValue(dependency.artifact.scheme)
            dependency.releaseDate?.let { date ->
                row.createCell(6).apply {
                    cellStyle = dateCellStyle
                    setCellValue(date)
                }
            }
        }

        // Write the output to a file
        val fileOut = FileOutputStream("report.xlsx")
        workbook.write(fileOut)
        fileOut.close()

        // Closing the workbook
        workbook.close()
    }
}
