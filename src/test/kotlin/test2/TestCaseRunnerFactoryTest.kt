package test2


import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import test.cases.TestCaseRunnerFactory
import test.cases.dataloader.TestType
import test.cases.runner.MultipleTagsSingleRead
import test.cases.runner.SingleTagMultipleAntennas

internal class TestCaseRunnerFactoryTest {

    @Test
    fun getRunnerByTypeSingleTagMultipleAntennas() {
        val testee = TestCaseRunnerFactory()
        val runnable: Runnable = testee.getRunnerByType(TestType.SingleTagMultipleAntennas)
        assertThat(runnable, CoreMatchers.instanceOf(SingleTagMultipleAntennas::class.java))
    }

    @Test
    fun getRunnerByTypeC() {
        val testee = TestCaseRunnerFactory()
        val runnable: Runnable = testee.getRunnerByType(TestType.MultipleTagsSingleRead)
        assertThat(runnable, CoreMatchers.instanceOf(MultipleTagsSingleRead::class.java))
    }
}