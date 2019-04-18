package test.cases

import test.cases.dataloader.TestType
import test.cases.runner.TestCaseA
import test.cases.runner.TestCaseB
import test.cases.runner.TestCaseC

class TestCaseRunnerFactory {
    fun getRunnerByType(testType: TestType): Runnable {
        when (testType) {
            TestType.A -> return TestCaseA()
            TestType.B -> return TestCaseB()
            TestType.C -> return TestCaseC()
        }
    }
}
