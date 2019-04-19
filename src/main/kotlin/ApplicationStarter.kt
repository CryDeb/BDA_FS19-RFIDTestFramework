import gui.GuiLess
import gui.ScannerAbstractionImpl

fun main() {
    val scannerAbstractionImpl = ScannerAbstractionImpl()
    val guiLess = GuiLess(scannerAbstractionImpl)
    val multipleSequentialTestExecutor = MultipleSequentialTestExecutor(guiLess)

    guiLess.subscribe(multipleSequentialTestExecutor)

    multipleSequentialTestExecutor.start()
}