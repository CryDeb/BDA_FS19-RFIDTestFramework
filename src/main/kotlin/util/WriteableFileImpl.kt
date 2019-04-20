package util

import java.io.File

class WriteableFileImpl(val file: File) : WriteableFile {
    init {
        changeFile(file)
    }

    override fun changeFile(file: File) {
        if (!file.exists()) {
            file.createNewFile()
        }
    }

    override fun writeToFile(text: String) {
        file.writeText(text)
    }
}
