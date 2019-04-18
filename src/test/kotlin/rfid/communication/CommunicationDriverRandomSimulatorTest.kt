package rfid.communication

import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import kotlin.random.Random

internal class CommunicationDriverRandomSimulatorTest {

    @org.junit.jupiter.api.Test
    fun getAllRfids() {
        val random: Random = getMockedRandomWithNextIntValue(3)
        val testee = CommunicationDriverRandomSimulator(random)
        assertEquals(4, testee.getAllRfids().size)
    }

    private fun getMockedRandomWithNextIntValue(nextRandomIntValue:Int):Random{
        val random: Random = mock(Random::class.java)
        `when`(random.nextInt()).thenReturn(nextRandomIntValue)
        return random
    }
}