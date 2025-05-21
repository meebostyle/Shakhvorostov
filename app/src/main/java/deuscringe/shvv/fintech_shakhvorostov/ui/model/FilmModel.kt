package deuscringe.shvv.fintech_shakhvorostov.ui.model




data class FilmModel (
    val filmId: Int,
    val name: String,
    val shortDesc: String,
    val posterUrlPreview: String,
    var isFavorite: Boolean = false,
)

data class FilmDetailedModel(
    val nameRu: String?,
    val posterUrl: String?,
    val genres: String?,
    val countries: String?,
    val description: String?,
)


