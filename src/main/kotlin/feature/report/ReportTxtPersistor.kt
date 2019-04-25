package feature.report

import util.FilenameValidator
import util.WriteableFile
import java.io.File

class ReportTxtPersistor(var file: WriteableFile) : ReportPersistor {
    override fun persistReports(reports: List<Report>) {
        reports.forEach(this::persistReport)
    }

    override fun changeFilename(filename: String) {
        val filenameIsValid = FilenameValidator().isFilenameValid(filename)
        if (filenameIsValid) {
            file.changeFile(File("$filename.txt"))
        }
        else {
            println("Filename is invalid")
        }
    }

    override fun persistReport(report: Report) {
        file.writeToFile(report.toString())
    }
}
