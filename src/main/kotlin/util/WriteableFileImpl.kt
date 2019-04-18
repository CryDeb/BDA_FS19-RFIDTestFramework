package util

import java.io.File

class WriteableFileImpl(val file: File): WriteableFile {
    init {
        if(!file.exists()){
            file.createNewFile()
        }
    }

    override fun writeToFile(text: String) {
        file.writeText(text)
    }
}