package rfid.communication

import com.sun.jna.Library
import com.sun.jna.Native
import com.sun.jna.Platform
import com.sun.jna.ptr.ByteByReference
import com.sun.jna.ptr.IntByReference
import rfid.communicationid.TagInformation
import util.UnsupportedPlattformException

class HyientechDeviceCommunicationDriver(dllFile: String) : CommunicationDriver {
    private val hyientechDriver: HyientechDriver
    private val baudRatePointer = ByteByReference(0)
    private val frmHandlePointer = IntByReference(0)
    private val deviceAddressPointer = ByteByReference(Byte.MIN_VALUE)
    private val devicePortPointer = IntByReference()


    init {
        if (!Platform.isWindows()) {
            throw UnsupportedPlattformException("Only Windows is supported")
        }
        hyientechDriver = Native.load(dllFile, HyientechDriver::class.java)
    }

    fun initialize(comPort: Int) {
        if (isError(
                hyientechDriver.AutoOpenComPort(
                    devicePortPointer,
                    deviceAddressPointer,
                    baudRatePointer,
                    frmHandlePointer
                )
            )
        ) {
            throw DeviceCommunicationException("Could not establish connection")
        }
        if (isError(hyientechDriver.OpenRf(deviceAddressPointer, frmHandlePointer.value))) {
            throw DeviceCommunicationException("Could not power on the inductive field")
        }
    }

    private fun isError(errorCode: Int): Boolean {
        return errorCode > 0
    }

    override fun getAllRfids(): List<TagInformation> {
        var tags = ByteByReference()
        createInventory(tags)
        return emptyList()
    }

    override fun getAllRfids(timeout: Int): List<TagInformation> {
        var inventoryScanTime = ByteByReference(timeout.toByte())
        System.out.println("Time: $timeout is " + timeout.div(10) + "sec")
        hyientechDriver.WriteInventoryScanTime(this.deviceAddressPointer, inventoryScanTime, frmHandlePointer.value)
        return getAllRfids()
    }

    override fun isSingleTagReachable(tagInformation: TagInformation): Boolean {
        val uid = ByteByReference()
        for (i in 0..tagInformation.uid.size) {
            uid.pointer.setByte(i.toLong(), tagInformation.uid[i])
        }
        val errorCode = ByteByReference()
        return !(isError(hyientechDriver.Select(deviceAddressPointer, uid, errorCode, frmHandlePointer.value)))
    }

    override fun switchToAntenna(antennaPosition: Int) {
        val antennaStatus = ByteByReference((1 shl antennaPosition).toByte())
        hyientechDriver.SetActiveANT(deviceAddressPointer, antennaStatus, frmHandlePointer.value)
    }

    private fun createInventory(tags: ByteByReference): ArrayList<TagInformation> {
        var tagsInformation = ArrayList<TagInformation>()
        var nmrTags = ByteByReference()
        var value = hyientechDriver.Inventory(
            deviceAddressPointer,
            WITHOUT_AFI,
            GARBAGE_BYTE_REFERENCE,
            tags,
            nmrTags,
            frmHandlePointer.value
        )
        val tagArray = tags.pointer.getByteArray(0, nmrTags.value * 9)
        if (value == 15 || value == 11) {
            for (i in 0..nmrTags.value) {
                var tagUid = ArrayList<Byte>()
                for (j in 0..8) {
                    tagUid.add(tagArray[i * j])
                }
                tagsInformation.add(TagInformation(tagUid))
            }
        }
        return tagsInformation
    }

    private interface HyientechDriver : Library {

        fun Select(
            ComAdr: ByteByReference, UID: ByteByReference,
            ErrorCode: ByteByReference, FrmHandle: Int
        ): Int
        fun OpenComPort(Port: Int, ComAdr: ByteByReference, Baud: ByteByReference, FrmHandle: IntByReference): Int
        fun CloseComPort(): Int
        fun CloseSpecComPort(FrmHandle: Int): Int
        fun OpenRf(comadr: ByteByReference, FrmHandle: Int): Int
        fun CloseRf(comadr: ByteByReference, FrmHandle: Int): Int
        fun WriteComAdr(ComAdr: ByteByReference, ComAdrData: ByteByReference, FrmHandle: Int): Int
        fun WriteInventoryScanTime(ComAdr: ByteByReference, InventoryScanTime: ByteByReference, FrmHandle: Int): Int
        fun SetGeneralOutput(ComAdr: ByteByReference, OutputData: ByteByReference, FrmHandle: Int): Int
        fun GetGeneralInput(ComAdr: ByteByReference, InputData: ByteByReference, FrmHandle: Int): Int
        fun SetRelay(ComAdr: ByteByReference, RelayAction: ByteByReference, FrmHandle: Int): Int
        fun SetActiveANT(ComAdr: ByteByReference, _ANT_Status: ByteByReference, FrmHandle: Int): Int
        fun GetANTStatus(ComAdr: ByteByReference, Get_ANT_Status: ByteByReference, FrmHandle: Int): Int
        fun SetUserDefinedBlockLength(ComAdr: ByteByReference, _Block_Len: ByteByReference, FrmHandle: Int): Int
        fun GetUserDefinedBlockLength(ComAdr: ByteByReference, _Block_Len: ByteByReference, FrmHandle: Int): Int
        fun SetScanMode(ComAdr: ByteByReference, _Scan_Mode_Data: ByteByReference, FrmHandle: Int): Int
        fun GetScanModeStatus(ComAdr: ByteByReference, _Scan_Mode_Status: ByteByReference, FrmHandle: Int): Int
        fun ReadScanModeData(ScanModeData: ByteByReference, ValidDataLength: IntByReference, FrmHandle: Int): Int
        fun SetAccessTime(ComAdr: ByteByReference, AccessTime: ByteByReference, FrmHandle: Int): Int
        fun GetAccessTime(ComAdr: ByteByReference, AccessTimeRet: ByteByReference, FrmHandle: Int): Int
        fun SetReceiveChannel(ComAdr: ByteByReference, ReceiveANT: ByteByReference, FrmHandle: Int): Int
        fun GetReceiveChannelStatus(ComAdr: ByteByReference, ReceiveANTStatus: ByteByReference, FrmHandle: Int): Int
        fun SetParseMode(ComAdr: ByteByReference, ParseMode: ByteByReference, FrmHandle: Int): Int
        fun GetParseMode(ComAdr: ByteByReference, ParseMode: ByteByReference, FrmHandle: Int): Int
        fun SetPwr(ComAdr: ByteByReference, _Pwr: ByteByReference, FrmHandle: Int): Int
        fun SetPwrByValue(ComAdr: ByteByReference, _PwrVal: ByteByReference, FrmHandle: Int): Int
        fun GetPwr(ComAdr: ByteByReference, _Pwr: ByteByReference, _PwrVal: ByteByReference, FrmHandle: Int): Int
        fun CheckAntenna(ComAdr: ByteByReference, _AntValid: ByteByReference, FrmHandle: Int): Int
        fun SyncScan(ComAdr: ByteByReference, _Sync: ByteByReference, FrmHandle: Int): Int
        fun GetReaderInformation(
            ComAdr: ByteByReference,
            VersionInfo: ByteByReference,
            ReaderType: ByteByReference,
            TrType: ByteByReference,
            InventoryScanTime: ByteByReference,
            FrmHandle: Int
        ): Int

        fun AutoOpenComPort(
            Port: IntByReference,
            ComAdr: ByteByReference,
            Baud: ByteByReference,
            FrmHandle: IntByReference
        ): Int


        fun Inventory(
            ComAdr: ByteByReference,
            State: ByteByReference,
            AFI: ByteByReference,
            DSFIDAndUID: ByteByReference,
            CardNum: ByteByReference,
            FrmHandle: Int
        ): Int

        fun StayQuiet(ComAdr: ByteByReference, UID: ByteByReference, ErrorCode: ByteByReference, FrmHandle: Int): Int
        fun ReadSingleBlock(
            ComAdr: ByteByReference,
            State: ByteByReference,
            UID: ByteByReference,
            BlockNum: Byte,
            BlockSecStatus: ByteByReference,
            Data: ByteByReference,
            ErrorCode: ByteByReference,
            FrmHandle: Int
        ): Int

        fun WriteSingleBlock(
            ComAdr: ByteByReference,
            State: ByteByReference,
            UID: ByteByReference,
            BlockNum: Byte,
            Data: ByteByReference,
            ErrorCode: ByteByReference,
            FrmHandle: Int
        ): Int

        fun LockBlock(
            ComAdr: ByteByReference,
            State: ByteByReference,
            UID: ByteByReference,
            BlockNum: Byte,
            ErrorCode: ByteByReference,
            FrmHandle: Int
        ): Int

        fun ReadMultipleBlock(
            ComAdr: ByteByReference,
            State: ByteByReference,
            UID: ByteByReference,
            BlockNum: Byte,
            BlockCount: Byte,
            BlockSecStatus: ByteByReference,
            Data: ByteByReference,
            ErrorCode: ByteByReference,
            intFrmHandle: Int
        ): Int

    }

    class DeviceCommunicationException(message: String) :
        Throwable("Error during the Communication with the Hyientech device: %s".format(message)) {
        constructor() : this("")
    }

    companion object {
        private val WITHOUT_AFI = ByteByReference(0)
        private val GARBAGE_BYTE_REFERENCE = ByteByReference(0)
    }
}