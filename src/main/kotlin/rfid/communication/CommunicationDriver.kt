package rfid.communication

interface CommunicationDriver {
    fun getAllRfids():List<TagInformation>
    fun getAllRfids(timeout: Int):List<TagInformation>
    fun isSingleTagReachable(id:TagInformation): Boolean
}