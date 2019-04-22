package test.cases.runner

import rfid.communication.CommunicationDriver
import rfid.communication.TagInformation

abstract class TestRunner(val communicationDriver: CommunicationDriver) : Runnable {
    var testResults = ""
    var amountOfReads = 10
    var singleTag = TagInformation(1)

    abstract override fun run()
}