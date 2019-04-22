package util

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class IntWriteableFileImplTest {

    @Test
    fun writeToFile() {
        val myFile = File("./Test.txt")
        assertFalse(myFile.exists())
        var writableFile = WriteableFileImpl(myFile)
        writableFile.writeToFile("Hello World!")
        assertTrue(myFile.exists())
        assertEquals(myFile.readText(), "Hello World!")
        myFile.delete()
    }
}
