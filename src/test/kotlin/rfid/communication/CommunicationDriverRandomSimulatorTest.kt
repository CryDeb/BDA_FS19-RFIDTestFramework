package rfid.communication

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import kotlin.random.Random

internal class CommunicationDriverRandomSimulatorTest {

    @Test
    fun getAllRfids() {
        val random: Random = getMockedRandomWithNextIntValue(3)
        val testee = CommunicationDriverRandomSimulator(random)
        assertEquals(4, testee.getAllRfids().size)
    }

    private fun getMockedRandomWithNextIntValue(nextRandomIntValue: Int): Random {
        val random: Random = mock(Random::class.java)
        `when`(random.nextInt()).thenReturn(nextRandomIntValue)
        `when`(random.nextInt(any(Int::class.java), any(Int::class.java))).thenReturn(nextRandomIntValue)
        return random
    }


}