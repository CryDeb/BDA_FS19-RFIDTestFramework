package feature.report

import util.WriteableFile

class ReportTxtPersistor(var file: WriteableFile) : ReportPersistor {
    override fun persistReport(report: Report) {
        file.writeToFile(report.toString())
    }
}
