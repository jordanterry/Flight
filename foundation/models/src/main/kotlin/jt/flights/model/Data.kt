package jt.flights.model

public sealed interface Data<out T> {

	public data class Some<T>(val data: T) : Data<T>

	public data class None<T>(val reason: String? = null) : Data<T>

	public data class Error<T>(val exception: Exception) : Data<T>
}