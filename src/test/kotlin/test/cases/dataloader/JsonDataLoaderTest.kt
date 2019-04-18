package test.cases.dataloader

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.ReadableFile
import util.ReadableFileStub
internal class JsonDataLoaderTest {

    @Test
    fun loadTestData() {
        var readableFile: ReadableFile = ReadableFileStub("{id:1,name:'test',preParameters:['Test'],postParameters:['test2'],testType:'A'}")
        var testee = JsonDataLoader(readableFile)
        assertEquals(TestData(1,"test",listOf("Test"),listOf("test2"),TestType.A), testee.loadTestData())
    }
}