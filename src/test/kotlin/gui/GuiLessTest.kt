package gui

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.io.*
import java.util.*
import java.util.regex.Pattern
import kotlin.test.assertEquals

internal class GuiLessTest {

    private val resultStream = ByteArrayOutputStream()
    private val outContent: PrintStream = PrintStream(resultStream)
    private val originalOut: PrintStream = System.out
    private val originalIn: InputStream = System.`in`
    private val scannerAbstractionImpl = ScannerStub("")

    @BeforeEach
    fun setUp() {
        System.setOut(outContent)
    }

    @AfterEach
    fun tearDown() {
        System.setOut(originalOut)
    }

    @Test
    fun displayMultiple() {
        val guiLess = GuiLess(scannerAbstractionImpl)
        val messages = listOf("Element A", "Element B", "Element C")
        val printOut = "Element A\\s*Element B\\s*Element C"
        guiLess.displayMultiple(messages)
        assert(Pattern.compile(printOut).matcher(resultStream.toString()).find())
    }

    @Test
    fun display() {
        val guiLess = GuiLess(scannerAbstractionImpl)
        val message = "This is a test"
        guiLess.display(message)
        assert(Pattern.compile(message).matcher(resultStream.toString()).find())
    }

    @Test
    fun testSubscriptionAndFunctionality() {
        val guiObserver = GuiObserverStub()
        val question = "Option 1?"
        val answer = "Yes"
        val scannerStub = ScannerStub(answer)
        val guiLess = GuiLess(scannerStub)
        guiLess.subscribe(guiObserver)
        guiLess.getUserInput(question)
        assert(Pattern.compile(question).matcher(resultStream.toString()).find())
        assertEquals(answer,guiObserver.userMessages.last())
        System.setIn(originalIn)
        guiLess.unsubscribe(guiObserver)
        scannerStub.returnValueForNext = "No"
        guiLess.getUserInput(question)
        assertEquals(answer, guiObserver.userMessages.last())
    }

    private class ScannerStub(var returnValueForNext:String): ScannerAbstraction {
        override fun next(): String {
            return returnValueForNext
        }
    }

    private class GuiObserverStub: GuiObserver{
        var userMessages = ArrayList<String>()
        override fun handleUserInput(userMessage: String) {
            userMessages.add(userMessage)
        }

    }
}