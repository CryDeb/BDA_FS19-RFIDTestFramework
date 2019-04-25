package test.cases.runner

import rfid.communication.CommunicationDriver
import rfid.communicationid.TagInformation
import kotlin.system.measureTimeMillis

class MultipleTagsSingleRead(communicationDriver: CommunicationDriver) : TestRunner(communicationDriver) {
    override fun run(parameters: Map<TestRunnerKeys, String>) {
        testResults = ""
        val tagsInVicinity = HashSet<TagInformation>()
        val timeForRead =
            measureTimeMillis {
                for (i in 0..100) {
                    tagsInVicinity.addAll(communicationDriver.getAllRfids(-15))
                }
            }
        if (tagsInVicinity.isEmpty()) {
            testResults += "Could not find any RFIDs\n"
        } else {
            testResults += "Anzahl Tags: ${tagsInVicinity.size}\n"
            tagsInVicinity.forEach { tagInformation ->
                testResults += "$tagInformation\n"
            }
        }
        testResults += "Seconds elapsed for read: ${timeForRead}\n"
    }
}
