package oop.solid.interfaceSegregation

import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.apache.poi.xwpf.usermodel.XWPFDocument
import org.apache.poi.xslf.usermodel.XMLSlideShow
import org.apache.poi.xslf.usermodel.XSLFSlide
import org.apache.poi.xslf.usermodel.XSLFTextShape
import java.awt.Rectangle
import java.io.*


/*
    Interface-Segregation Principle states that:
    - classes should not be forced to implement functions they don't use
    - ISP makes code less complex and therefore easier to maintain

*/


// file handler implements multiple applications in the same interface (Word, Excel, PowerPoint) - breaks principle
interface FileHandler {
    fun readWord(filePath: String): String
    fun writeWord(filePath: String, content: String)
    fun readExcel(filePath: String): List<List<String>>
    fun writeExcel(filePath: String, data: List<List<String>>)
    fun readPowerPoint(filePath: String): String
    fun writePowerPoint(filePath: String, content: String)
}


// Concrete implementation for file operations (violating ISP)
class FileHandlerImpl : FileHandler {
    override fun readWord(filePath: String): String {
        val doc = XWPFDocument(FileInputStream(File(filePath)))
        val paragraph = doc.paragraphs
        val sb = StringBuilder()
        paragraph.forEach { sb.append(it.text) }
        doc.close()
        return sb.toString()
    }

    override fun writeWord(filePath: String, content: String) {
        val doc = XWPFDocument()
        val paragraph = doc.createParagraph()
        val run = paragraph.createRun()
        run.setText(content)
        val fileOut = FileOutputStream(filePath)
        doc.write(fileOut)
        fileOut.close()
        doc.close()
    }

    override fun readExcel(filePath: String): List<List<String>> {
        val workbook: Workbook = XSSFWorkbook(FileInputStream(File(filePath)))
        val sheet: Sheet = workbook.getSheetAt(0)
        val excelData = mutableListOf<List<String>>()
        for (row in sheet) {
            val rowData = mutableListOf<String>()
            for (cell in row) {
                rowData.add(getCellValue(cell))
            }
            excelData.add(rowData)
        }
        workbook.close()
        return excelData
    }

    override fun writeExcel(filePath: String, data: List<List<String>>) {
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Sheet1")
        for ((rowNum, rowList) in data.withIndex()) {
            val row = sheet.createRow(rowNum)
            for ((cellNum, cellValue) in rowList.withIndex()) {
                row.createCell(cellNum).setCellValue(cellValue)
            }
        }
        val fileOut = FileOutputStream(filePath)
        workbook.write(fileOut)
        fileOut.close()
        workbook.close()
    }

    override fun readPowerPoint(filePath: String): String {
        val ppt = XMLSlideShow(FileInputStream(File(filePath)))
        val slides: List<XSLFSlide> = ppt.slides
        val sb = StringBuilder()

        for (slide in slides) {
            sb.append(slide.slideNumber).append(". ") // Assuming you want to append slide number
            for (shape in slide) {
                if (shape is XSLFTextShape) {
                    sb.append(shape.text).append("\n")
                }
            }
        }

        ppt.close()
        return sb.toString()
    }

    override fun writePowerPoint(filePath: String, content: String) {
        val ppt = XMLSlideShow()
        val slide = ppt.createSlide()
        val title = slide.createTextBox()
        title.setText(content)
        title.anchor = Rectangle(50, 50, 400, 200)
        val fileOut = FileOutputStream(filePath)
        ppt.write(fileOut)
        fileOut.close()
        ppt.close()
    }

    private fun getCellValue(cell: Cell): String {
        return when (cell.cellType) {
            CellType.STRING -> cell.stringCellValue
            CellType.NUMERIC -> cell.numericCellValue.toString()
            CellType.BOOLEAN -> cell.booleanCellValue.toString()
            else -> ""
        }
    }
}

fun main() {

    // the same File Handler is responsible for all operations, therefore code is too complex - breaks principle
    val fileHandler: FileHandler = FileHandlerImpl()

    val docPath = "${filesPath}doc_output.docx"
    fileHandler.writeWord(docPath, "Violation of Interface Segregation Principle in Word file.")
    val wordContent = fileHandler.readWord(docPath)
    println("Word content: $wordContent")

    val excelPath = "${filesPath}excel_output.xlsx"
    fileHandler.writeExcel(
        excelPath,
        listOf(listOf("1", "Violation"), listOf("2", "of"), listOf("3", "Interface"), listOf("4", "Segregation"))
    )
    val excelData = fileHandler.readExcel(excelPath)
    println("Excel content: $excelData")

    val powerPointPath = "${filesPath}ppt_output.pptx"
    fileHandler.writePowerPoint(powerPointPath, "Violation of Interface Segregation Principle in PowerPoint file.")
    val powerPointContent = fileHandler.readPowerPoint(powerPointPath)
    println("PowerPoint content: $powerPointContent")
}
