package feature.report

interface ReportPersistor {
    fun persistReport(report: Report)
    fun changeFilename(filename: String)
}
