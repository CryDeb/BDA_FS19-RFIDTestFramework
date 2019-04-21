package feature.report

import test.cases.dataloader.TestData

class Report(private val testData: TestData) {
    var preTestParameterInput: List<String> = mutableListOf()
    var postTestParameterInput: List<String> = mutableListOf()
    var testResults: String = ""

    override fun toString(): String {
        return "$testData\n" +
                "PreParameterInput: $preTestParameterInput\n" +
                "PostParameterInput: $postTestParameterInput\n" +
                "TestResults: $testResults"
    }
}
