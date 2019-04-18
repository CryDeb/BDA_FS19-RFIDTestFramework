package feature.report

import test.cases.dataloader.TestData
import test.cases.dataloader.TestType

class Report(val testData: TestData) {
    var preTestParameterInput: List<String> = mutableListOf()
    var postTestParameterInput: List<String> = mutableListOf()

    override fun toString(): String {
        return "%s  PreParameterInput: %s  PostParameterInput: %s"
            .format(testData.toString(), preTestParameterInput.toString(), postTestParameterInput.toString())
    }
}
