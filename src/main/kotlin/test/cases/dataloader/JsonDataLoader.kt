package test.cases.dataloader

import com.google.gson.Gson
import util.ReadableFile

class JsonDataLoader(private val file: ReadableFile) : DataLoader {
    override fun loadTestData(): TestData {
        var gson = Gson()
        var jsonString: String = file.getText()
        return gson.fromJson(jsonString, TestData::class.java)
    }
}