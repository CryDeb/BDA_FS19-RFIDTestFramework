package gui

import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class GuiLessTestIntegration {
    private val resultStream = ByteArrayOutputStream()
    private val outContent: PrintStream = PrintStream(resultStream)
    private val originalOut: PrintStream = System.out
    private val scannerAbstractionImpl = ScannerStub("")

    @BeforeEach
    fun setUp() {
        System.setOut(outContent)
    }

    @AfterEach
    fun tearDown() {
        System.setOut(originalOut)
    }

    private class ScannerStub(var returnValueForNext: String) : ScannerAbstraction {
        override fun next(): String {
            return returnValueForNext
        }
    }

    @Test
    fun displayMultiple() {
        val guiLess = GuiLess(scannerAbstractionImpl)
        val messages = listOf("Element A", "Element B", "Element C")
        guiLess.displayMultiple(messages)
        messages.forEach { message ->
            val result = resultStream.toString()
            val containsMessage = CoreMatchers.containsString(message)
            MatcherAssert.assertThat(result, containsMessage)
        }
    }

    @Test
    fun display() {
        val guiLess = GuiLess(scannerAbstractionImpl)
        val message = "This is a test"
        guiLess.display(message)
        val result = resultStream.toString()
        val containsMessage = CoreMatchers.containsString(message)
        MatcherAssert.assertThat(result, containsMessage)
    }

}
