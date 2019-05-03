package util

import java.io.File
import java.io.FileNotFoundException

class ReadableResourceFile(resourceName: String) : ReadableFile {
    private var file: File

    init {
        val path: String = ReadableResourceFile::class.java.getResource("../$resourceName").path
        file = File(path)
        if (!file.exists() && !file.canRead()) throw FileNotFoundException()
    }

    override fun getText(): String {
        return file.inputStream().bufferedReader().readText()
    }
}
