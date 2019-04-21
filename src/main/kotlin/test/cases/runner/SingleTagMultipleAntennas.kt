package test.cases.runner

import rfid.communication.CommunicationDriver

class SingleTagMultipleAntennas(communicationDriver: CommunicationDriver) : TestRunner(communicationDriver) {
    private var antenna = 1
    override fun run() {
        testResults = ""
        for (i in 1..amountOfReads) {
            communicationDriver.switchToAntenna(getAntenna())
            testResults += "${communicationDriver.isSingleTagReachable(singleTag).toString().capitalize()}\n"
        }
    }

    private fun getAntenna(): Int {
        antenna = if (antenna == 1) 2 else 1
        return antenna
    }
}
