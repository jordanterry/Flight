package jt.flights.model

sealed interface Data<out T> {

	data class Some<T>(val data: T) : Data<T>

	data class None<T>(val reason: String? = null) : Data<T>

	data class Error<T>(val exception: Exception) : Data<T>
}