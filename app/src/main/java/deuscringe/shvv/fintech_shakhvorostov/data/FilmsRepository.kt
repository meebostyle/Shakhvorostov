package deuscringe.shvv.fintech_shakhvorostov.data

import deuscringe.shvv.fintech_shakhvorostov.data.model.FilmCollectionResponse
import deuscringe.shvv.fintech_shakhvorostov.data.model.FilmDetailedResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FilmsRepository {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://kinopoiskapiunofficial.tech")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(FilmsService::class.java)

    suspend fun getFilmsCollection ():FilmCollectionResponse {
        return service.getFilmsCollection()
    }

    suspend fun getFilmDetailed(filmId: Int): FilmDetailedResponse{
        return service.getFilmDetailed(filmId)
    }
}