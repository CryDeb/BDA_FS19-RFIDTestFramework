package test.cases.dataloader

interface DataLoader {
    fun loadSingleTestData(): TestData
    fun loadMultipleTestData(): ArrayList<TestData>
}