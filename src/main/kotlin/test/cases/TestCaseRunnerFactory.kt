package test.cases

import test.cases.dataloader.TestType
import test.cases.runner.MultipleTagsMultipleReads
import test.cases.runner.MultipleTagsSingleRead
import test.cases.runner.SingleTagMultipleAntennas
import test.cases.runner.SingleTagMultipleReads

class TestCaseRunnerFactory {
    fun getRunnerByType(testType: TestType): Runnable {
        return when (testType) {
            TestType.SingleTagMultipleReads -> SingleTagMultipleReads()
            TestType.SingleTagMultipleAntennas -> SingleTagMultipleAntennas()
            TestType.MultipleTagsSingleRead -> MultipleTagsSingleRead()
            TestType.MultipleTagsMultipleReads -> MultipleTagsMultipleReads()
        }
    }
}
