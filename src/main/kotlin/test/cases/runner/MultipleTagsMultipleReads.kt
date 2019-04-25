package test.cases.runner

import rfid.communication.CommunicationDriver
import rfid.communicationid.TagInformation
import kotlin.system.measureTimeMillis

class MultipleTagsMultipleReads(communicationDriver: CommunicationDriver) : TestRunner(communicationDriver) {
    override fun run(parameters: Map<TestRunnerKeys, String>) {
        testResults = ""
        for (i in 1..amountOfReads) {
            val tagsInVicinity = mutableListOf<TagInformation>()
            val timeForRead = measureTimeMillis { tagsInVicinity.addAll(communicationDriver.getAllRfids(200)) }
            if (tagsInVicinity.isEmpty()){
                testResults += "Could not find any RFIDs\n"
            }
            else {
                testResults += "Number of Tags: ${tagsInVicinity.count()}\n"
                tagsInVicinity.forEach { tagInformation ->
                    testResults += "$tagInformation\n"
                }
            }
            testResults += "Seconds elapsed for read: $timeForRead ms\n"
        }
    }
}
