package test.cases.runner

import rfid.communication.AntennaPositions
import rfid.communication.CommunicationDriver
import rfid.communicationid.TagInformation

class SingleTagMultipleAntennas(communicationDriver: CommunicationDriver) : TestRunner(communicationDriver) {
    private var antenna = AntennaPositions.ONE
    override fun run(parameters: Map<TestRunnerKeys, String>) {
        testResults = ""
        for (i in 1..amountOfReads) {
            communicationDriver.switchToAntenna(getToggledAntennaPosition())
            testResults += "${communicationDriver.isSingleTagReachable(
                TagInformation(
                    TagInformation.getByteListForHexString(
                        parameters.get(TestRunnerKeys.TAG_ID_AS_HEX).orEmpty()
                    )
                )
            ).toString().capitalize()}\n"
        }
    }

    private fun getToggledAntennaPosition(): AntennaPositions {
        antenna = if (antenna == AntennaPositions.ONE) AntennaPositions.ONE else AntennaPositions.TWO
        return antenna
    }
}
