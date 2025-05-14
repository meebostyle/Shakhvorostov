package deuscringe.shvv.fintech_shakhvorostov.domain

import deuscringe.shvv.fintech_shakhvorostov.data.model.Films
import deuscringe.shvv.fintech_shakhvorostov.data.model.Genre

data class FilmContent(
    val filmId: Int,
    val nameRu: String,
    val genres: List<Genre>,
    val year: String,
    val imgPreView: String,
)

fun Films.toFilmParsing(): FilmContent{
    return FilmContent(
        filmId = filmId,
        nameRu = nameRu,
        genres = genres,
        year = year,
        imgPreView = imgPreView
    )
}

