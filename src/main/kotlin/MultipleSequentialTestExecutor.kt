import feature.report.Report
import feature.report.ReportPersistor
import gui.GuiLess
import gui.GuiObserver
import test.cases.dataloader.DataLoader
import test.cases.dataloader.TestData

abstract class MultipleSequentialTestExecutor(
    private val guiLess: GuiLess,
    private val dataLoader: DataLoader,
    private val reportPersistor: ReportPersistor
) : GuiObserver {
    private var testDataList: ArrayList<TestData> = dataLoader.loadMultipleTestData()
    private var lastReport: Report? = null

    private fun displayHeader() {
        val header = "" +
                "*----------------------------------------------------------------*\n" +
                "|                                                     ;;;..      |\n" +
                "|                                                      ``;;..    |\n" +
                "|  RFID Test Framework                                ..  `;;;   |\n" +
                "|                                                     ~~   ;;;   |\n" +
                "*----------------------------------------------------------------*\n"
        guiLess.display(header)
    }

    public fun start() {
        while (true) {
            displayHeader()
            guiLess.display("---- Tests ----")
            testDataList.iterator().forEach { testData -> guiLess.display("${testData.id} - ${testData.name}") }
            guiLess.getUserInput("Choose test (nr), or quit (q)")
        }
    }

    private fun quit() {
        if (lastReport != null) reportPersistor.persistReport(lastReport!!)
        System.exit(0)
    }

    private fun executeTest(testId: Int) {
        guiLess.display("Execute test $testId")
    }

    override fun handleUserInput(userMessage: String) {
        try {
            val testId: Int = userMessage.toInt()
            testDataList.iterator().forEach { testData ->
                if (testData.id.equals(testId)) this.executeTest(testId)
            }
        } catch (error: NumberFormatException) {
            when (userMessage) {
                "q" -> this.quit()
                else -> guiLess.display("Unknown option $userMessage")
            }
        }
    }
}