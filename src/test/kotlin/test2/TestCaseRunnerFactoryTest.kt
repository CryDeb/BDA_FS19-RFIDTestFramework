package test2


import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import test.cases.TestCaseRunnerFactory
import test.cases.dataloader.TestType
import test.cases.runner.SingleTagMultipleAntennas
import test.cases.runner.MultipleTagsSingleRead

internal class TestCaseRunnerFactoryTest {

    @Test
    fun getRunnerByTypeB() {
        val testee = TestCaseRunnerFactory()
        val runnable: Runnable = testee.getRunnerByType(TestType.B)
        assertThat(runnable, CoreMatchers.instanceOf(SingleTagMultipleAntennas::class.java))
    }

    @Test
    fun getRunnerByTypeC() {
        val testee = TestCaseRunnerFactory()
        val runnable: Runnable = testee.getRunnerByType(TestType.C)
        assertThat(runnable, CoreMatchers.instanceOf(MultipleTagsSingleRead::class.java))
    }
}