package test.cases.runner

import rfid.communication.CommunicationDriver
import rfid.communicationid.TagInformation

class SingleTagMultipleReads(communicationDriver: CommunicationDriver) : TestRunner(communicationDriver) {
    override fun run(parameters: Map<TestRunnerKeys, String>) {
        testResults = ""
        for (i in 1..amountOfReads) {
            testResults += "${communicationDriver.isSingleTagReachable(
                TagInformation(
                    TagInformation.getByteListForHexString(
                        parameters.get(
                            TestRunnerKeys.TAG_ID_AS_HEX
                        ).orEmpty()
                    )
                )
            ).toString().capitalize()}\n"
        }
    }
}
