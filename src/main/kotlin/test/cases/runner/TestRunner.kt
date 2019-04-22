package test.cases.runner

import rfid.communication.CommunicationDriver

abstract class TestRunner(val communicationDriver: CommunicationDriver) {
    var testResults = ""
    var amountOfReads = 10

    abstract fun run(parameters: Map<TestRunnerKeys, String>)
}

enum class TestRunnerKeys {
    TAG_ID_AS_HEX

}
