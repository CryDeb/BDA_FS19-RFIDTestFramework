package gui

import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*
import java.io.*
import java.util.*
import java.util.regex.Pattern
import kotlin.test.assertEquals

internal class GuiLessTest {

    private val resultStream = ByteArrayOutputStream()
    private val outContent: PrintStream = PrintStream(resultStream)
    private val originalOut: PrintStream = System.out

    @BeforeEach
    fun setUp() {
        System.setOut(outContent)
    }

    @AfterEach
    fun tearDown() {
        System.setOut(originalOut)
    }

    @Test
    fun testSubscription() {
        // Arrange
        val guiObserver = GuiObserverStub()
        val question = "Option 1?"
        val answer = "Yes"
        val scannerStub = ScannerStub(answer)
        val guiLess = GuiLess(scannerStub)

        // Act
        guiLess.subscribe(guiObserver)
        guiLess.getUserInput(question)
        guiLess.unsubscribe(guiObserver)
        scannerStub.returnValueForNext = "No"
        guiLess.getUserInput(question)

        // Verify
        assertEquals(answer, guiObserver.userMessages.last())
    }

    @Test
    fun testGetUserInput() {
        val guiObserver = GuiObserverStub()
        val question = "Option 1?"
        val answer = "Yes"
        val scannerStub = ScannerStub(answer)
        val guiLess = GuiLess(scannerStub)
        guiLess.subscribe(guiObserver)

        guiLess.getUserInput(question)
        assertThat(resultStream.toString(), containsString(question))
        assertEquals(answer,guiObserver.userMessages.last())
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