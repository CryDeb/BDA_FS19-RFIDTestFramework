import feature.report.Report
import feature.report.ReportPersistor
import gui.GuiLess
import gui.GuiObserver
import rfid.communicationid.TagInformation
import test.cases.TestCaseRunnerFactory
import test.cases.dataloader.DataLoader
import test.cases.dataloader.TestData
import test.cases.dataloader.TestType
import test.cases.runner.TestRunner
import test.cases.runner.TestRunnerKeys
import java.time.LocalDateTime
import kotlin.collections.Map as Map1

class MultipleSequentialTestExecutor(
    private val guiLess: GuiLess,
    dataLoader: DataLoader,
    private val reportPersistor: ReportPersistor,
    private val testCaseRunnerFactory: TestCaseRunnerFactory
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
    private var tagIdRequested = false
    private var tagId: List<Byte> = ArrayList<Byte>()

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
            guiLess.askUserForInput(
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
        changeParam = true
        var testHeader: String
        val preParameterListOfTestRun: MutableList<String> = mutableListOf()
        val postParameterListOfTestRun: MutableList<String> = mutableListOf()
        val testRunner: TestRunner = testCaseRunnerFactory.getRunnerByType(testData.testType)
        val testParameters = HashMap<TestRunnerKeys, String>()

        while (testRunning) {
            testHeader = "\n" +
                    "-------------------------------------------------------------\n" +
                    "${testData.id} - ${testData.name}\n" +
                    "${testData.testType} Run Nr. $run\n" +
                    "-------------------------------------------------------------\n"
            guiLess.display(testHeader)

            if (changeParam) {
                testParameters.clear()
                preParameterListOfTestRun.clear()

                for (preParameter in testData.preParameters.iterator()) {
                    guiLess.askUserForInput(preParameter)
                    preParameterListOfTestRun.add(lastUserInputForParam)
                }

                if (testData.testType == TestType.SingleTagMultipleAntennas ||
                    testData.testType == TestType.SingleTagMultipleReads
                ) {
                    val tagInformation = requestTagId()
                    testParameters.set(TestRunnerKeys.TAG_ID_AS_HEX, tagInformation.toString())
                }

                if (testData.preParameters.contains("Anzahl Leseversuche")) {
                    try {
                        val amountOfReads =
                            preParameterListOfTestRun[testData.preParameters.indexOf("Anzahl Leseversuche")].toInt()
                        testRunner.amountOfReads = amountOfReads
                    } catch (exception: NumberFormatException) {
                        guiLess.display("Anzahl Leseversuche sollte eine Ganzzahl sein, defaulting zu 10")
                    }
                }
                changeParam = false
            }

            testRunner.run(testParameters)
            val testResults = testRunner.testResults

            guiLess.display("\nResults:\n$testResults")

            postParameterListOfTestRun.clear()
            changeParam = true
            for (postParameter in testData.postParameters.iterator()) {
                guiLess.askUserForInput(postParameter)
                postParameterListOfTestRun.add(lastUserInputForParam)
            }
            changeParam = false

            lastReport = generateReport(testData, preParameterListOfTestRun, postParameterListOfTestRun, testResults)

            guiLess.askUserForInput(
                "(s)ave report\n" +
                        "(c)hange Pretest parameters\n" +
                        "(r)erun with current config\n" +
                        "(b)ack\n" +
                        "(q)uit\n" +
                        "?"
            )

            if (userHasSaved) {
                guiLess.askUserForInput(
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
        postParameterList: List<String>,
        testResults: String
    ): Report {
        val report = Report(testData)
        report.preTestParameterInput = preParameterList
        report.postTestParameterInput = postParameterList
        report.testResults = testResults
        return report
    }

    private fun saveReportWithAsking() {
        if (lastReport != null && lastTest != null) {
            saving = true
            val dateTime = LocalDateTime.now()
            val fileNameDateTime =
                "${dateTime.year}.${dateTime.monthValue}.${dateTime.dayOfMonth}-${dateTime.hour}.${dateTime.minute}.${dateTime.second}"
            filename = "TestReport-${lastTest!!.id}-$fileNameDateTime"
            guiLess.display("Save as $filename")
            guiLess.askUserForInput("(y)es or write your own")
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

    private fun requestTagId(): TagInformation {
        tagIdRequested = true
        // Declaration and assignment CAN'T be joined, as askUserForInput invoked later changes tagId
        var tagInformation: TagInformation
        while (tagIdRequested) {
            guiLess.askUserForInput("Tag ID")
        }
        tagInformation = TagInformation(tagId)
        return tagInformation
    }

    override fun handleUserInput(userMessage: String) {
        try {
            val userInputAsInt: Int = userMessage.toInt()
            if (!testRunning) testDataList.filter { data -> data.id == userInputAsInt }.forEach { data ->
                run = 1; this.executeTest(data)
            }
            if (changeParam) {
                lastUserInputForParam = userMessage
            }
        } catch (exception: NumberFormatException) {
            if (tagIdRequested && userMessage.length > 1) {
                tagId = TagInformation.getByteListForHexString(userMessage)
                tagIdRequested = false
            } else if (!saving && !changeParam && !tagIdRequested) {
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
