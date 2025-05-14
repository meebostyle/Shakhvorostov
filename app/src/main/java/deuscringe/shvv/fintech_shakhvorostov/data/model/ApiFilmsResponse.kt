package deuscringe.shvv.fintech_shakhvorostov.data.model

data class FilmCollectionResponse(
    val pagesCount: Int,
    val filmsCollection: List<Films>
        )

data class Films (
    val filmId: Int,
    val nameRu: String,
    val genres: List<Genre>,
    val year: String,
    val imgPreView: String
)

data class Genre (
    val genre: String
)
