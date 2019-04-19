import gui.GuiLess
import gui.GuiObserver
import test.cases.dataloader.DataLoader
import test.cases.dataloader.TestData

class MultipleSequentialTestExecutor(private val guiLess: GuiLess, private val dataLoader: DataLoader) : GuiObserver {
    private var testDataList: ArrayList<TestData> = dataLoader.loadMultipleTestData()

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

    fun quit() {
        // TODO Implement report generation
        System.exit(0)
    }

    fun executeTest(testId: Int) {
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