import gui.GuiLess
import gui.GuiObserver

class MultipleSequentialTestExecutor(private val guiLess: GuiLess) : GuiObserver {
    override fun handleUserInput(userMessage: String) {
        when (userMessage) {
            "q" -> this.quit()
            else -> guiLess.display("Unknown option $userMessage")
        }
    }

    private fun displayHeader() {
        val header = "" +
                "*----------------------------------------------------------------*\n" +
                "|                                                     ;;;..      |\n" +
                "|                                                      ``;;..    |\n" +
                "|  RFID Test Framework                                ..  `;;;   |\n" +
                "|                                                     ~~   ;;;   |\n" +
                "*----------------------------------------------------------------*\n" +
                "\n"
        guiLess.display(header)
    }

    fun start() {
        while (true) {
            displayHeader()
            // TODO dynamic test numbers
            guiLess.getUserInput("Choose test (nr), or quit (q)")
        }
    }

    fun quit() {
        // TODO Implement report generation
        System.exit(0)
    }

}