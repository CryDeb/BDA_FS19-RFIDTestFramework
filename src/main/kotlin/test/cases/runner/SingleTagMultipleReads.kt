package test.cases.runner

import rfid.communication.CommunicationDriver

class SingleTagMultipleReads(communicationDriver: CommunicationDriver) : TestRunner(communicationDriver) {
    override fun run() {
        testResults = ""
        for (i in 1..amountOfReads) {
            testResults += "${communicationDriver.isSingleTagReachable(singleTag).toString().capitalize()}\n"
        }
    }
}
