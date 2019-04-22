package rfid.communication

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import rfid.communicationid.TagInformation
import kotlin.test.assertTrue

@Disabled
internal class IntHyientechDeviceCommunicationDriverTest {
    @Test
    fun initialize() {
        var hyientechDriver = HyientechDeviceCommunicationDriver("Basic")
        hyientechDriver.initialize()
        hyientechDriver.getAllRfids().forEach { tag -> System.out.println(tag.toString()) }
    }

    @Test
    fun getAllRfids() {
        var hyientechDriver = HyientechDeviceCommunicationDriver("Basic")
        hyientechDriver.initialize()
        assertTrue(hyientechDriver.isSingleTagReachable(TagInformation(listOf(-32, 4, 1, 80, 79, 31, -1, -69))))
    }
}