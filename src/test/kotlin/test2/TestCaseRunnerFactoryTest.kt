package test2


import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import rfid.communication.CommunicationDriverRandomSimulator
import test.cases.TestCaseRunnerFactory
import test.cases.dataloader.TestType
import test.cases.runner.MultipleTagsSingleRead
import test.cases.runner.SingleTagMultipleAntennas
import kotlin.random.Random

internal class TestCaseRunnerFactoryTest {
    val communicationDriver = CommunicationDriverRandomSimulator(Random(1337))

    @Test
    fun getRunnerByTypeSingleTagMultipleAntennas() {
        val testee = TestCaseRunnerFactory(communicationDriver)
        val runnable: Runnable = testee.getRunnerByType(TestType.SingleTagMultipleAntennas)
        assertThat(runnable, CoreMatchers.instanceOf(SingleTagMultipleAntennas::class.java))
    }

    @Test
    fun getRunnerByTypeMultipleTagsSingleRead() {
        val testee = TestCaseRunnerFactory(communicationDriver)
        val runnable: Runnable = testee.getRunnerByType(TestType.MultipleTagsSingleRead)
        assertThat(runnable, CoreMatchers.instanceOf(MultipleTagsSingleRead::class.java))
    }
}