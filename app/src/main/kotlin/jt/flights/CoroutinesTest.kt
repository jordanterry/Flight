package jt.flights

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.RuntimeException

fun main() = runBlocking {
    val coroutineExceptionHandler = CoroutineExceptionHandler { context, throwable ->
        println(throwable.message)
    }
    val j1 = GlobalScope.launch(CoroutineName("Hello Scope")) {
        throw RuntimeException("hello")
    }
    val j2 = CoroutineScope(CoroutineName("New Scope")).launch(coroutineExceptionHandler) {
        throw RuntimeException("hello2")
    }
    j1.join()
    j2.join()
    println("am I still here")
}