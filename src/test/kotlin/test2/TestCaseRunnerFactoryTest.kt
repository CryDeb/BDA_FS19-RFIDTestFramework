package test2


import test.cases.dataloader.TestType
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import test.cases.TestCaseRunnerFactory
import test.cases.runner.TestCaseB
import test.cases.runner.TestCaseC

internal class TestCaseRunnerFactoryTest {

    @Test
    fun getRunnerByTypeB() {
        val testee = TestCaseRunnerFactory()
        val runnable: Runnable = testee.getRunnerByType(TestType.B)
        assertThat(runnable, CoreMatchers.instanceOf(TestCaseB::class.java))
    }

    @Test
    fun getRunnerByTypeC() {
        val testee = TestCaseRunnerFactory()
        val runnable: Runnable = testee.getRunnerByType(TestType.C)
        assertThat(runnable, CoreMatchers.instanceOf(TestCaseC::class.java))
    }
}