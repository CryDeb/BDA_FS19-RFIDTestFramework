package test.cases.runner

import rfid.communication.CommunicationDriver
import rfid.communication.TagInformation
import kotlin.system.measureTimeMillis

class MultipleTagsMultipleReads(communicationDriver: CommunicationDriver) : TestRunner(communicationDriver) {
    override fun run() {
        testResults = ""
        for (i in 1..amountOfReads) {
            val tagsInVicinity = mutableListOf<TagInformation>()
            val timeForRead = measureTimeMillis { tagsInVicinity.addAll(communicationDriver.getAllRfids(10)) }
            // TODO what happens when timeout is reached? Has to be handled
            tagsInVicinity.forEach { tagInformation ->
                testResults += "$tagInformation\n"
            }
            testResults += "Seconds elapsed for read: ${timeForRead/1000}"
        }
    }
}
