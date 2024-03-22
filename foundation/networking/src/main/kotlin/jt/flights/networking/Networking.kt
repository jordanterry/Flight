package jt.flights.networking


import okhttp3.Request
import okhttp3.Response

public class Resource<Value> internal constructor(
	internal val request: () -> Request,
	internal val success: suspend (Response) -> Result<Value>,
	internal val failure: suspend (Exception) -> Result<Value>,
) {
	public companion object
}

public fun <Value> Resource(
	request: () -> Request,
	success: suspend (Response) -> Result<Value>,
): Resource<Value> {
	return Resource(request = request, success = success, failure = { Result.failure(it) })
}

public fun <Value, New> Resource<Value>.map(convert: (Value) -> New): Resource<New> {
	return Resource(
		request = request,
		success = { success(it).map(convert) },
		failure = { failure(it).map(convert) },
	)
}

public class Network internal constructor(
	internal val resolve: suspend (Request) -> Response,
)

public suspend fun <Value> Network.fetch(
	resource: Resource<Value>
): Result<Value> {
	return try {
		val request = resource.request()
		val response = resolve(request)
		resource.success(response)
	} catch (exception: Exception) {
		resource.failure(exception)
	}
}