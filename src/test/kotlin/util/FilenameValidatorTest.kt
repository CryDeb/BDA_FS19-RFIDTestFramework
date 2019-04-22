package util

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.DisabledOnOs
import org.junit.jupiter.api.condition.OS
import kotlin.test.assertFalse

class FilenameValidatorTest {
    @Test
    fun IValidFilename() {
        val validFilename = "testfile.txt"
        val testee = FilenameValidator()
        assert(testee.isFilenameValid(validFilename))
    }

    @Test
    @DisabledOnOs(OS.LINUX)
    fun IInvalidFilename() {
        val invalidFilename = "\\/:*?<>|.txt"
        val testee = FilenameValidator()
        assertFalse(testee.isFilenameValid(invalidFilename))
    }

}