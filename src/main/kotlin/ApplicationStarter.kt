import feature.report.ReportTxtPersistor
import gui.GuiLess
import gui.ScannerAbstractionImpl
import rfid.communication.CommunicationDriverRandomSimulator
import test.cases.TestCaseRunnerFactory
import test.cases.dataloader.JsonDataLoader
import util.ReadableResourceFile
import util.WriteableFileImpl
import java.io.File
import kotlin.random.Random

fun main() {
    val scannerAbstractionImpl = ScannerAbstractionImpl()
    val guiLess = GuiLess(scannerAbstractionImpl)
    val jsonReadableFile = ReadableResourceFile("package.json")
    val dataLoader = JsonDataLoader(jsonReadableFile)
    val txtFile = File("./Report.txt")
    val reportPersistor = ReportTxtPersistor(WriteableFileImpl(txtFile))
    val random = Random(1337)
    val communicationDriver = CommunicationDriverRandomSimulator(random)
    val testCaseRunnerFactory = TestCaseRunnerFactory(communicationDriver)
    val multipleSequentialTestExecutor = MultipleSequentialTestExecutor(guiLess, dataLoader, reportPersistor, testCaseRunnerFactory)

    guiLess.subscribe(multipleSequentialTestExecutor)

    multipleSequentialTestExecutor.start()
}
