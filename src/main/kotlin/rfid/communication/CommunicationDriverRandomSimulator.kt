package rfid.communication

import rfid.communicationid.TagInformation
import kotlin.random.Random

class CommunicationDriverRandomSimulator(private val random: Random) : CommunicationDriver {
    override fun getAllRfids(): List<TagInformation> {
        val mutableList: MutableList<TagInformation> = mutableListOf()
        for (i in 0..random.nextInt()) {
            val list = ArrayList<Byte>()
            for (i in 0..8) {
                list.add(random.nextInt().toByte())
            }
            mutableList.add(TagInformation(list))
        }
        return mutableList
    }

    override fun getAllRfids(timeout: Int): List<TagInformation> {
        return getAllRfids()
    }

    override fun isSingleTagReachable(id: TagInformation): Boolean {
        return random.nextBoolean()
    }

    override fun switchToAntenna(antennaPosition: AntennaPositions) {
        // no implementation needed for simulation
    }
}