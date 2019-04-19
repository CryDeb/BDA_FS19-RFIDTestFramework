package test.cases.dataloader

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.ReadableFile
import util.ReadableFileStub

internal class JsonDataLoaderTest {

    @Test
    fun loadTestData() {
        var readableFile: ReadableFile =
            ReadableFileStub("{id:1,name:'test',preParameters:['Test'],postParameters:['test2'],testType:'A'}")
        var testee = JsonDataLoader(readableFile)
        assertEquals(TestData(1, "test", listOf("Test"), listOf("test2"), TestType.A), testee.loadSingleTestData())
    }

    @Test
    fun loadMultipleTestData() {
        val singleTest = "{ id:1, name:'test', preParameters:['Test'], postParameters:['test2'], testType:'A' }"
        val testData = TestData(1, "test", listOf("Test"), listOf("test2"), TestType.A)
        var readableFile: ReadableFile = ReadableFileStub("[$singleTest, $singleTest]")
        var testee = JsonDataLoader(readableFile)
        assertEquals(mutableListOf(testData, testData), testee.loadMultipleTestData())
    }
}