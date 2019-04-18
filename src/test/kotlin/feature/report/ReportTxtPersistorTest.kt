package feature.report

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import test.cases.dataloader.TestData
import util.WriteableFileImpl
import util.WriteableFileStub
import java.io.File

internal class ReportTxtPersistorTest {

    @Disabled
    @Test
    fun checkPersisting() {
        val file = WriteableFileStub()
        val testee = ReportTxtPersistor(file)
        val report = Report(getTestData("TEST"))
        testee.persistReport(report)
        assertEquals(report.toString(), file.textStoredToFile)
    }

    @Disabled
    @Test
    fun IStoreToRealFile() {
        val file = WriteableFileImpl(File(System.getProperty("user.home") + "/test.txt"))
        val testee = ReportTxtPersistor(file)
        testee.persistReport(Report(getTestData("a lot of values")))
        assertTrue(file.file.exists())
    }

    private fun getTestData(toStringValue: String): TestData {
        val testData = mock(TestData::class.java)
        org.mockito.Mockito.`when`(testData.toString()).thenReturn(toStringValue)
        return testData
    }
}
