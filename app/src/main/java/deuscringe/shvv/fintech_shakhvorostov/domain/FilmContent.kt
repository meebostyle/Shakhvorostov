package deuscringe.shvv.fintech_shakhvorostov.domain

import deuscringe.shvv.fintech_shakhvorostov.data.model.Genre

data class FilmContent(
    val filmId: Int,
    val nameRu: String,
    val genres: Genre,
    val year: String,
    val posterUrlPreview: String,
)



data class FilmDetailedContent(
    val nameRu: String,
    val genres: List<Genre>,
    val countries : List<String>,
    val desc: String,
    val posterUrl: String,
)


