package test.cases.runner

import rfid.communication.CommunicationDriver
import rfid.communication.TagInformation
import kotlin.system.measureTimeMillis

class MultipleTagsSingleRead(communicationDriver: CommunicationDriver) : TestRunner(communicationDriver) {
    override fun run() {
        testResults = ""
        val tagsInVicinity = mutableListOf<TagInformation>()
        val timeForRead = measureTimeMillis { tagsInVicinity.addAll(communicationDriver.getAllRfids(10)) }
        if (tagsInVicinity.isEmpty()){
            testResults += "Could not find any RFIDs\n"
        }
        else {
            tagsInVicinity.forEach { tagInformation ->
                testResults += "$tagInformation\n"
            }
        }
        testResults += "Seconds elapsed for read: ${timeForRead/1000}\n"
    }
}
