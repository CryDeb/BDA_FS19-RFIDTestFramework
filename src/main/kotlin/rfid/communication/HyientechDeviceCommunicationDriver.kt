package rfid.communication

import com.sun.jna.Library
import com.sun.jna.Native
import com.sun.jna.Platform
import com.sun.jna.ptr.ByteByReference
import com.sun.jna.ptr.IntByReference
import util.UnsupportedPlattformException

class HyientechDeviceCommunicationDriver(dllFile: String) : CommunicationDriver {
    private val hyientechDriver: HyientechDriver
    private val baudRatePointer = ByteByReference(0)
    private val frmHandlePointer = IntByReference(0)
    private val deviceAddressPointer = ByteByReference(Byte.MIN_VALUE)
    private val devicePortPointer = IntByReference(0)


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
    }

    private fun isError(errorCode: Int): Boolean {
        return errorCode < 0
    }

    override fun getAllRfids(): List<TagInformation> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAllRfids(timeout: Int): List<TagInformation> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isSingleTagReachable(id: TagInformation): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun switchToAntenna(antennaPosition: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private interface HyientechDriver : Library {
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
    }

    class DeviceCommunicationException(message: String) :
        Throwable("Error during the Communication with the Hyientech device: %s".format(message)) {
        constructor() : this("")
    }
}
