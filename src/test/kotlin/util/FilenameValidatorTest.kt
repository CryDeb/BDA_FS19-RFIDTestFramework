package util

import org.junit.jupiter.api.Test
import kotlin.test.assertFalse

class FilenameValidatorTest {
    @Test
    fun IValidFilename() {
        val validFilename = "testfile.txt"
        val testee = FilenameValidator()
        assert(testee.isFilenameValid(validFilename))
    }

    @Test
    fun IInvalidFilename() {
        val invalidFilename = "\\/:*?<>|.txt"
        val testee = FilenameValidator()
        assertFalse(testee.isFilenameValid(invalidFilename))
    }

}