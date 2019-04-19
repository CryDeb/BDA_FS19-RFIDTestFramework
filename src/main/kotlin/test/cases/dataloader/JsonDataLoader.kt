package test.cases.dataloader

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import util.ReadableFile

class JsonDataLoader(private val file: ReadableFile) : DataLoader {
    override fun loadSingleTestData(): TestData {
        var gson = Gson()
        var jsonString: String = file.getText()
        return gson.fromJson(jsonString, TestData::class.java)
    }

    override fun loadMultipleTestData(): ArrayList<TestData> {
        var gson = Gson()
        var jsonString: String = file.getText()
        return gson.fromJson(jsonString, TypeTokenTestData().type)
    }

    private class TypeTokenTestData : TypeToken<ArrayList<TestData>>()
}