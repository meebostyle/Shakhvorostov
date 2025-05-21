package deuscringe.shvv.fintech_shakhvorostov.data.model

data class FilmCollectionResponse(
    val pagesCount: Int,
    val films: List<Films>
        )

data class Films (
    val filmId: Int,
    val nameRu: String,
    val genres: List<Genre>,
    val year: String,
    val posterUrlPreview: String
)

data class Genre (
    val genre: String
){
    override fun toString(): String {
        return genre
    }
}

data class FilmDetailedResponse(
    val nameRu: String,
    val posterUrl: String,
    val genres: List<Genre>,
    val countries: List<Country>,
    val description: String,
)

data class Country (
    val country: String
){
    override fun toString(): String {
        return country
    }
}
