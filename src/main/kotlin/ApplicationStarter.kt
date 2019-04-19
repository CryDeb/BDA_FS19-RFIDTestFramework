import gui.GuiLess
import gui.ScannerAbstractionImpl
import test.cases.dataloader.JsonDataLoader
import util.ReadableFileImpl
import java.io.File

fun main() {
    val scannerAbstractionImpl = ScannerAbstractionImpl()
    val guiLess = GuiLess(scannerAbstractionImpl)
    val jsonFile = File(MultipleSequentialTestExecutor::class.java.getResource("package.json").path)
    val jsonReadableFile = ReadableFileImpl(jsonFile)
    val dataLoader = JsonDataLoader(jsonReadableFile)
    val multipleSequentialTestExecutor = MultipleSequentialTestExecutor(guiLess, dataLoader)

    guiLess.subscribe(multipleSequentialTestExecutor)

    multipleSequentialTestExecutor.start()
}