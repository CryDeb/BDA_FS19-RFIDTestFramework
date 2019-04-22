package test.cases.dataloader

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test

internal class TestDataTest {

    @Test
    fun testForEquality() {
        val testee = TestData(0,"Test", emptyList(), emptyList(), TestType.SingleTagMultipleReads.toString())
        assertEquals(testee, TestData(0,"Test", emptyList(), emptyList(), TestType.SingleTagMultipleReads))
    }

    @Test
    fun testForUniquality(){
        val testee = TestData(0,"Test", emptyList(), emptyList(), TestType.SingleTagMultipleReads.toString())
        assertNotEquals(testee, TestData(0,"Test", emptyList(), emptyList(), TestType.SingleTagMultipleAntennas))
    }
}
