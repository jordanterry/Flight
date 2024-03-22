package jt.flights.networking

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

public suspend inline fun Call.await(): Response {
	return suspendCancellableCoroutine { continuation ->
		val callback = ContinuationCallback(this, continuation)
		enqueue(callback)
		continuation.invokeOnCancellation(callback)
	}
}

public class ContinuationCallback(
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

public suspend fun <T> Call.makeRequestAndUseBody(block: suspend (ResponseBody) -> T): Data<T> {
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
		ioException.printStackTrace()
		Data.Error(
			exception = ioException
		)
	} catch (serializationException: SerializationException) {
		serializationException.printStackTrace()
		Data.Error(
			exception = serializationException
		)
	} finally {
		response?.close()
		body?.close()
	}
}