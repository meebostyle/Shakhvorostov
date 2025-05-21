package deuscringe.shvv.fintech_shakhvorostov.data

import deuscringe.shvv.fintech_shakhvorostov.data.model.FilmCollectionResponse
import deuscringe.shvv.fintech_shakhvorostov.data.model.FilmDetailedResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface FilmsService {

    @Headers("X-API-KEY: $X_API_KEY")
    @GET("api/v2.2/films/top")
    suspend fun getFilmsCollection (
        @Query("type") collection: String = "TOP_100_POPULAR_FILMS"
    ):FilmCollectionResponse

    @Headers("X-API-KEY: $X_API_KEY")
    @GET("api/v2.2/films/{id}")
    suspend fun getFilmDetailed (
        @Path("id") filmId: Int
    ): FilmDetailedResponse


    companion object {
    const val X_API_KEY = " e30ffed0-76ab-4dd6-b41f-4c9da2b2735b"
    }
}