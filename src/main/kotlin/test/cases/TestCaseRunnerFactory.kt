package test.cases

import rfid.communication.CommunicationDriver
import test.cases.dataloader.TestType
import test.cases.runner.*

class TestCaseRunnerFactory(val communicationDriver: CommunicationDriver) {
    fun getRunnerByType(testType: TestType): TestRunner {
        return when (testType) {
            TestType.SingleTagMultipleReads -> SingleTagMultipleReads(communicationDriver)
            TestType.SingleTagMultipleAntennas -> SingleTagMultipleAntennas(communicationDriver)
            TestType.MultipleTagsSingleRead -> MultipleTagsSingleRead(communicationDriver)
            TestType.MultipleTagsMultipleReads -> MultipleTagsMultipleReads(communicationDriver)
        }
    }
}
