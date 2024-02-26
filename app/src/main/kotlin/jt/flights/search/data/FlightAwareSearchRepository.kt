package jt.flights.search.data

/**
 * Flight aware implementation of the [SearchRepository].
 */
class FlightAwareSearchRepository(
    private val searchDataSource: SearchDataSource,
) : SearchRepository {
    override suspend fun search(flightNumber: String): SearchRepository.SearchResults {
        val searchResult = searchDataSource.search(flightNumber = flightNumber)
        return if (searchResult.isSuccess) {
            val results = searchResult.getOrNull()
            if (!results.isNullOrEmpty()) {
                SearchRepository.SearchResults.Found(results = results)
            } else {
                SearchRepository.SearchResults.NotFound
            }
        } else {
            SearchRepository.SearchResults.NotFound
        }
    }
}
