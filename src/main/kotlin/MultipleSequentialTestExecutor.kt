import feature.report.Report
import feature.report.ReportPersistor
import gui.GuiLess
import gui.GuiObserver
import test.cases.dataloader.DataLoader
import test.cases.dataloader.TestData

class MultipleSequentialTestExecutor(
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

    fun start() {
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

    private fun executeTest(testData: TestData) {
        guiLess.display("Execute test ${testData.id}")
    }

    override fun handleUserInput(userMessage: String) {
        try {
            val testId: Int = userMessage.toInt()
            testDataList
                .stream()
                .filter { data -> data.id == testId }
                .forEach(this::executeTest)
        } catch (error: NumberFormatException) {
            when (userMessage) {
                "q" -> this.quit()
                else -> guiLess.display("Unknown option $userMessage")
            }
        }
    }
}
