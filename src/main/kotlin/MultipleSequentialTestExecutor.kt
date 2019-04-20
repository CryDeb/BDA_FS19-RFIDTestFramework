import feature.report.Report
import feature.report.ReportPersistor
import gui.GuiLess
import gui.GuiObserver
import test.cases.dataloader.DataLoader
import test.cases.dataloader.TestData
import java.time.LocalDateTime
import kotlin.collections.ArrayList

class MultipleSequentialTestExecutor(
    private val guiLess: GuiLess,
    private val dataLoader: DataLoader,
    private val reportPersistor: ReportPersistor
) : GuiObserver {
    private var testDataList: ArrayList<TestData> = dataLoader.loadMultipleTestData()
    private var lastReport: Report? = null
    private var lastTest: TestData? = null

    private var testRunning: Boolean = false
    private var changeParam = true
    private var saving = false
    private var userHasSaved = false
    private var filename = "Report"
    private var run = 1
    private var lastUserInputForParam = ""

    private fun displayHeader() {
        val header = "" +
                "                                                                             .:;:.         \n" +
                "                                                                           ;;;;;;;;;       \n" +
                "                                                                         ;;;.     .;;:     \n" +
                "                                                                        ;;.  .::,   .;;    \n" +
                "                                                                           :;;;;;;;.  ;;   \n" +
                "                                                                          ;;.    `:;: `;:  \n" +
                ".---------------------------------------------------------------~,;,...   .  :;;:  .;: ,;  \n" +
                "|                                                                ;;         ;;::;;:  ;  ;: \n" +
                "|                                                                ;;         .    .;: ;; :; \n" +
                "|                                                                ;;           ,;, ,; .; .; \n" +
                "|                                                                ;;          .;;;` ;, ; `; \n" +
                "|                                                                ;;          ,;;;, ;, ; `; \n" +
                "|                                                                ;;           ;;;  ;. ; `; \n" +
                "|                                                                ;;               ,; ,; ,; \n" +
                "|        RFID Test Framework                                     ;;               :, ;: ;; \n" +
                "|        Dane Wicki & Pascal Baumann                             ;;                 :, ;;  \n" +
                "|                                                                ;;                   :;`  \n" +
                "|        Hochschule Luzern                                       ;;  ;;; ;;; ; ;;;     `   \n" +
                "|                                                                ;;  : ; ;   ; , `;        \n" +
                "|                                                                ;;  ;;. ;:, ; ,  ; ;;     \n" +
                "|                                                                ;;  : : ;   ; ;:;` ;;     \n" +
                "|                                                                ;;  ` ` `   ` ``   ;;     \n" +
                "`----------------------------------------------------------------´;;;;;;;;;;;;;;;;;;;`     \n"
        guiLess.display(header)
    }

    fun start() {
        while (true) {
            displayHeader()
            guiLess.display("---- Tests ----")
            testDataList.iterator().forEach { testData -> guiLess.display("${testData.id} - ${testData.name}") }
            guiLess.display("")
            guiLess.getUserInput(
                "Choose test (nr)\n" +
                        "(q)uit\n" +
                        "?"
            )
        }
    }

    private fun quit() {
        saveReportWithoutAsking()
        System.exit(0)
    }

    private fun executeTest(testData: TestData) {
        testRunning = true
        lastTest = testData
        userHasSaved = false
        var testHeader: String
        val preParameterListOfTestRun: MutableList<String> = mutableListOf()
        val postParameterListOfTestRun: MutableList<String> = mutableListOf()
        while (testRunning) {
            testHeader = "\n" +
                    "-------------------------------------------------------------\n" +
                    "${testData.id} - ${testData.name}\n" +
                    "${testData.testType} Run Nr. $run\n" +
                    "-------------------------------------------------------------\n"
            guiLess.display(testHeader)
            if (changeParam) {
                preParameterListOfTestRun.clear()
                for (preParameter in testData.preParameters.iterator()) {
                    guiLess.getUserInput(preParameter)
                    preParameterListOfTestRun.add(lastUserInputForParam)
                }
                changeParam = false
            }
            // TODO run test
            // TODO show results

            postParameterListOfTestRun.clear()
            changeParam = true
            for (postParameter in testData.postParameters.iterator()) {
                guiLess.getUserInput(postParameter)
                postParameterListOfTestRun.add(lastUserInputForParam)
            }
            changeParam = false

            lastReport = generateReport(testData, preParameterListOfTestRun, postParameterListOfTestRun)

            guiLess.getUserInput(
                "(s)ave report\n" +
                        "(c)hange Pretest parameters\n" +
                        "(r)erun with current config\n" +
                        "(b)ack\n" +
                        "(q)uit\n" +
                        "?"
            )

            if (userHasSaved) {
                guiLess.getUserInput(
                        "(c)hange Pretest parameters\n" +
                        "(r)erun with current config\n" +
                        "(b)ack\n" +
                        "(q)uit\n" +
                        "?"
                )
            }
        }
    }

    private fun generateReport(
        testData: TestData,
        preParameterList: List<String>,
        postParameterList: List<String>
    ): Report {
        val report = Report(testData)
        report.preTestParameterInput = preParameterList
        report.postTestParameterInput = postParameterList
        return report
    }

    private fun saveReportWithAsking() {
        if (lastReport != null && lastTest != null) {
            saving = true
            val dateTime = LocalDateTime.now()
            val fileNameDateTime = "${dateTime.year}.${dateTime.month}.${dateTime.dayOfMonth}-${dateTime.hour}.${dateTime.minute}.${dateTime.second}"
            filename = "TestReport-${lastTest!!.id}-$fileNameDateTime"
            guiLess.display("Save as $filename")
            guiLess.getUserInput("(y)es or write your own")
        } else {
            guiLess.display("\nThere is no report to save!\n")
        }
    }

    private fun saveReportWithoutAsking() {
        reportPersistor.changeFilename(filename)
        if (lastReport != null) reportPersistor.persistReport(lastReport!!)
        saving = false
        userHasSaved = true
    }

    override fun handleUserInput(userMessage: String) {
        try {
            val testId: Int = userMessage.toInt()
            if (!testRunning) testDataList.filter { data -> data.id == testId }.forEach { data ->
                run = 1; this.executeTest(data)
            }
        } catch (error: NumberFormatException) {
            if (!saving && !changeParam) {
                when (userMessage) {
                    "q" -> this.quit()
                    "b" -> if (testRunning) testRunning = false
                    "c" -> if (testRunning) changeParam = true
                    "s" -> saveReportWithAsking()
                    "r" -> run += 1
                    else -> guiLess.display("Unknown option $userMessage")
                }
            } else if (saving) {
                when (userMessage) {
                    "y" -> this.saveReportWithoutAsking()
                    else -> {
                        filename = userMessage
                        this.saveReportWithoutAsking()
                    }
                }
            } else if (changeParam) {
                lastUserInputForParam = userMessage
            }
        }
    }
}
