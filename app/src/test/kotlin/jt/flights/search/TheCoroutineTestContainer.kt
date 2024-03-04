package jt.flights.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Test
import java.lang.RuntimeException
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue
import kotlin.test.fail

@OptIn(DelicateCoroutinesApi::class)
@Suppress("DANGEROUS_CHARACTERS")
class TheCoroutineTestContainer {

    @Test
    fun `What happens when we launch a coroutine and throw an exception but do not catch it?`() {
        assertFailsWith<RuntimeException> {
            runTest {
                val job = launch {
                    throw RuntimeException("Runtime exception thrown in a test.")
                }
                job.join()
            }
        }
    }

    @Test
    fun `What happens when we launch a coroutine and throw and we catch it inside the launch?`() {
        runTest {
            val thrownException = RuntimeException("Runtime exception thrown in a test.")
            val job = launch {
                try {
                    throw thrownException
                } catch (runtimeException: RuntimeException) {
                    assertEquals(thrownException, runtimeException)
                }
            }
            job.join()
        }
    }


    @Test
    fun `When we throw an exception but catch it outside the launch, it is not caught by the try catch`() {
        assertFailsWith<RuntimeException> {
            runTest {
                val thrownException = RuntimeException("Runtime exception thrown in a test.")
                try {
                    val job = launch {
                        throw thrownException
                    }
                    job.join()
                } catch (runtimeException: RuntimeException) {
                    assertEquals(thrownException, runtimeException)
                }
            }
        }
    }


    @Test
    fun `When we throw an exception but catch it outside a nested launch, it is not caught by the try catch`() {
        assertFailsWith<RuntimeException> {
            runTest {
                val thrownException = RuntimeException("Runtime exception thrown in a test.")
                val job = launch {
                    try {
                        launch {
                            throw thrownException
                        }
                    } catch (runtimeException: RuntimeException) {
                        assertEquals(thrownException, runtimeException)
                    }
                }
                job.join()
            }
        }
    }

    @Test
    fun `What happens when we test with a ViewModelScope?`() {
        val viewModel = object : ViewModel() {
            fun startAsync() {
                viewModelScope.launch {
                    throw RuntimeException("We're testing here!")
                }
            }
        }

        assertFailsWith<RuntimeException> {
            viewModel.startAsync()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `What happens when we test with a ViewModelScope, but catch outside the launch?`() {
        Dispatchers.setMain(Dispatchers.Default)
        val viewModel = object : ViewModel() {
            fun startAsync() {
                try {
                    viewModelScope.launch {
                        println("throw")
                        throw RuntimeException("We're testing here!")
                    }
                } catch (exception: RuntimeException) {
                    fail("Exception is not caught here!")
                }
            }
        }

        viewModel.startAsync()
        Thread.sleep(500) // Coroutines + not using runTest
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `What happens when we test with a ViewModelScope, but catch inside the launch?`() {
        Dispatchers.setMain(Dispatchers.Default)
        val viewModel = object : ViewModel() {
            fun startAsync() {
                viewModelScope.launch {
                    try {
                        throw RuntimeException("We're testing here!")
                    } catch (exception: Exception) {
                        fail("Exception is not caught here!")
                    }
                }
            }
        }

        viewModel.startAsync()
        Thread.sleep(500)
    }


    @Test
    fun `What happens when we start a deferred coroutine, throw and catch the throwable?`() {
        runTest {
            val thrownException = RuntimeException("Runtime exception thrown in a test.")
            val deferred = GlobalScope.async {
                throw thrownException
            }
            try {
                deferred.await()
            } catch (runtimeException: RuntimeException) {
                assertEquals(thrownException.message, runtimeException.message)
            }
        }
    }

    /**
     * 1. Launch a coroutine scope with runTest
     * 2. Launch an async coroutine using launch, put the job into variable named `job`
     * 3. Suspend using `job.join()` until the coroutine is cancelled
     * 4. Call cancel on the continuation
     * 5. Confirm that `job` is cancelled
     */
    @Test
    fun `What happens when we cancel a launched coroutine with a continuation, without an exception?`() {
        runTest {
            val job = launch {
                suspendCancellableCoroutine<Unit> { continuation ->
                    continuation.cancel()
                }
            }
            job.join()
            assertTrue(job.isCancelled)
        }
    }

    @Test
    fun `What happens when we cancel a launched coroutine with a continuation, with a RuntimeException?`() {
        assertFailsWith<RuntimeException> {
            runTest {
                val job = launch {
                    suspendCancellableCoroutine<Unit> { continuation ->
                        continuation.cancel(RuntimeException("We're testing here!"))
                    }
                }
                job.join()
                assertTrue(job.isCancelled)
            }
        }
    }

    @Test
    fun `What happens when we cancel two launched coroutines with a continuation, with different exceptions?`() {
        assertFailsWith<ClassNotFoundException> {
            runTest {
                val job1 = launch {
                    delay(400)
                    suspendCancellableCoroutine<Unit> { continuation ->
                        continuation.cancel(RuntimeException("We're testing here!"))
                    }
                }
                val job2 = launch {
                    suspendCancellableCoroutine<Unit> { continuation ->
                        continuation.cancel(ClassNotFoundException("We're testing here!"))
                    }
                }
                joinAll(job1, job2)
                assertTrue(job1.isCancelled)
                assertTrue(job2.isCancelled)
            }
        }
    }

    /**
     * 1. Starts two coroutines which are designed to fail independently
     * 2. The first coroutine waits for 10,000 milliseconds and will throw an exception upon completion
     * 3. The second coroutine immediately throws a `ClassNotFoundException`
     * 4. When the second coroutine fails, delay will cancel with a `CancellationException` and then the `RuntimeException` is thrown
     * 5. We catch the exception thrown from the `runTest` block
     * 6. Confirm that the top-level exception is the `ClassNotFoundException`
     * 7. Confirm that the `RuntimeException` is contained in the suppress block
     */
    @Test
    fun `What happens when we cancel two launched coroutines, with different exceptions, and catch the exception out of the runTest?`() {
        try {
            runTest {
                val job1 = launch {
                    try {
                        delay(10_000)
                    } finally {
                        throw RuntimeException("We're testing here!")
                    }
                }
                val job2 = launch {
                    throw ClassNotFoundException("We're testing here!")
                }
                joinAll(job1, job2)
                assertTrue(job1.isCancelled)
                assertTrue(job2.isCancelled)
            }
        } catch (exception: Exception) {
            assertIs<ClassNotFoundException>(exception)
            assertEquals(1, exception.suppressed.size)
            assertIs<RuntimeException>(exception.suppressed.first())
        }
    }

    @Test
    fun `What happens when we cancel two launched coroutines, with different exceptions, and catch the exception inside of the runTest?`() {
        try {
            runTest {
                lateinit var job1: Job
                lateinit var job2: Job
                try {
                    job1 = launch {
                        try {
                            delay(10_000)
                        } finally {
                            throw RuntimeException("We're testing here!")
                        }
                    }
                    job2 = launch {
                        throw ClassNotFoundException("We're testing here!")
                    }
                    joinAll(job1, job2)
                } catch (exception: Exception) {
                    assertIs<CancellationException>(exception)
                }
                assertTrue(job1.isCancelled)
                assertTrue(job2.isCancelled)
            }
        } catch (exception: Exception) {
            assertIs<ClassNotFoundException>(exception)
            assertEquals(1, exception.suppressed.size)
            assertIs<RuntimeException>(exception.suppressed.first())
        }
    }

    @Test
    fun `What happens when we cancel a launched coroutine with a job, without an exception?`() {
        runTest {
            val job = launch {
                delay(5_000)
                fail("This should never be called.")
            }
            job.cancel()
            assertTrue(job.isCancelled)
        }
    }

    @Test
    fun `What happens when we cancel a launched coroutine with a job, with a cancellation exception?`() {
        runTest {
            val job = launch {
                delay(5_000)
                fail("This should never be called.")
            }
            job.cancel(CancellationException("We're testing here!"))
            assertTrue(job.isCancelled)
        }
    }

    @Test
    fun `What happens when we wrap a cancelled coroutine but do not let the cancellation exception through?`() {
        runTest {
            val j1 = launch {
                try {
                    suspendCancellableCoroutine<Unit> { continuation ->
                        continuation.cancel()
                    }
                } catch (throwable: Throwable) {
                    assertIs<CancellationException>(throwable)
                }
            }
            j1.join()
            assertFalse(j1.isCancelled)
        }
    }

    @Test
    fun `What happens when we throw with a RuntimeException, catch it and a cancelled coroutine but do not let the cancellation exception through?`() {
        runTest {
            val thrownException = RuntimeException("Runtime exception thrown in a test.")
            val j1 = launch {
                try {
                    suspendCancellableCoroutine<Unit> { continuation ->
                        continuation.cancel(thrownException)
                    }
                } catch (throwable: RuntimeException) {
                    assertIs<RuntimeException>(throwable)
                }
            }
            j1.join()
            assertFalse(j1.isCancelled) // Job has not been cancelled despite cancellation
            assertFalse(coroutineContext[Job.Key]!!.isCancelled) // Scope has not been cancelled despite cancellation
        }
    }


    @Test
    fun `What happens when we throw with a RuntimeException, and do not catch it?`() {
        assertFailsWith<RuntimeException> {
            runTest {
                val thrownException = RuntimeException("Runtime exception thrown in a test.")
                val j1 = launch {
                    suspendCancellableCoroutine<Unit> { continuation ->
                        continuation.cancel(thrownException)
                    }
                }
                j1.join()
                fail("Coroutine should be cancelled.")
            }
        }
    }
}
