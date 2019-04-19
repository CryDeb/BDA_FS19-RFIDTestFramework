package gui

import java.util.*

class ScannerAbstractionImpl : ScannerAbstraction {
    val scanner = Scanner(System.`in`)
    override fun next(): String {
        return scanner.next()
    }
}