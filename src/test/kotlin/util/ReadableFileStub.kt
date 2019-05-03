package util

class ReadableFileStub(val returnValue: String) : ReadableFile {
    override fun getText(): String {
        return returnValue
    }
}
