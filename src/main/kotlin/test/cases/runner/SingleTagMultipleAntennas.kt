package test.cases.runner

import rfid.communication.AntennaPositions
import rfid.communication.CommunicationDriver
import rfid.communicationid.TagInformation
import kotlin.system.measureTimeMillis

class SingleTagMultipleAntennas(communicationDriver: CommunicationDriver) : TestRunner(communicationDriver) {
    private var antenna = AntennaPositions.ONE
    override fun run(parameters: Map<TestRunnerKeys, String>) {
        testResults = ""
        for (i in 1..amountOfReads) {
            val timeinMs = measureTimeMillis {
                communicationDriver.switchToAntenna(getToggledAntennaPosition())
            }
            testResults += "Zeit für Antennenwechsel ($antenna): $timeinMs ms    Tag verfügbar: ${communicationDriver.isSingleTagReachable(
                TagInformation(
                    TagInformation.getByteListForHexString(
                        parameters.get(TestRunnerKeys.TAG_ID_AS_HEX).orEmpty()
                    )
                )
            ).toString().capitalize()}\n"
        }
    }

    private fun getToggledAntennaPosition(): AntennaPositions {
        antenna = if (antenna == AntennaPositions.ONE) AntennaPositions.TWO else AntennaPositions.ONE
        return antenna
    }
}
