package feature.report

interface ReportPersistor {
    fun persistReport(report: Report)
    fun persistReports(reports: List<Report>)
    fun changeFilename(filename: String)
}
