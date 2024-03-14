package jt.flights.networking

import com.newrelic.agent.android.NewRelic
import jt.flights.model.Data
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.CompletionHandler
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.serialization.SerializationException
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

suspend inline fun Call.await(): Response {
	return suspendCancellableCoroutine { continuation ->
		val callback = ContinuationCallback(this, continuation)
		enqueue(callback)
		continuation.invokeOnCancellation(callback)
	}
}

class ContinuationCallback(
	private val call: Call,
	private val continuation: CancellableContinuation<Response>
) : Callback, CompletionHandler {

	override fun onResponse(call: Call, response: Response) {
		continuation.resume(response)
	}

	override fun onFailure(call: Call, e: IOException) {
		if (!call.isCanceled()) {
			continuation.resumeWithException(e)
		}
	}

	override fun invoke(cause: Throwable?) {
		try {
			call.cancel()
		} catch (_: Throwable) {
		}
	}
}

suspend fun <T> Call.makeRequestAndUseBody(block: suspend (ResponseBody) -> T): Data<T> {
	var response: Response? = null
	var body: ResponseBody? = null
	return try {
		response = await()
		if (response.isSuccessful) {
			body = response.body ?: return Data.None("No response body.")
			return Data.Some(block(body))
		} else {
			return Data.None("Response code: ${response.code}")
		}
	} catch (ioException: IOException) {
		NewRelic.recordHandledException(ioException)
		ioException.printStackTrace()
		Data.Error(
			exception = ioException
		)
	} catch (serializationException: SerializationException) {
		NewRelic.recordHandledException(serializationException)
		serializationException.printStackTrace()
		Data.Error(
			exception = serializationException
		)
	} finally {
		response?.close()
		body?.close()
	}
}