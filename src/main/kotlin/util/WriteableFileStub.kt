package util

class WriteableFileStub : WriteableFile {
    var textStoredToFile: String = ""

    override fun writeToFile(text: String) {
        textStoredToFile += text
    }

}