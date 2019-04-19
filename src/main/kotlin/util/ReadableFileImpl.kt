package util

import java.io.File
import java.io.FileNotFoundException

class ReadableFileImpl(private val file: File) : ReadableFile {
    constructor(filePath: String) : this(File(filePath))

    init {
        if (!file.exists() && !file.canRead()) throw FileNotFoundException()
    }

    override fun getText(): String {
        return file.inputStream().bufferedReader().readText()
    }
}
