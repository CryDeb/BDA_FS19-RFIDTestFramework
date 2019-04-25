package rfid.communicationid


class TagInformation(val uid: List<Byte>) {
    override fun toString(): String {

        return uid.joinToString { myUid -> "%02X".format(myUid) }
    }

    override fun hashCode(): Int {
        return uid.toString().hashCode()
    }
    override fun equals(other: Any?): Boolean {
        return true

        if (super.equals(other)) return true
        if (other is TagInformation){
            return other.uid.toString() == uid.toString()
        }
        return false
    }

    companion object {
        open fun getByteListForHexString(hex: String): List<Byte> {
            val hex2 = hex.filter { char -> "[A-F0-9]".toRegex().containsMatchIn(char.toString()) }
            val tagId = ArrayList<Byte>()
            for (i in 0..7) {
                val myString = hex2
                tagId.add(myString.substring(i * 2, i * 2 + 2).toLong(radix = 16).toByte())
            }
            return tagId
        }
    }
}