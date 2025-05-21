package deuscringe.shvv.fintech_shakhvorostov.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import deuscringe.shvv.fintech_shakhvorostov.data.FilmsRepository
import deuscringe.shvv.fintech_shakhvorostov.ui.model.FilmDetailedModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PopularListDetailedViewModel (): ViewModel() {

    private val _isProgressBarVisible = MutableStateFlow<Boolean?>(null)
    val isProgressBarVisible = _isProgressBarVisible.asStateFlow()

    private val _isErrorVisible = MutableStateFlow<Boolean?>(null)
    val isErrorVisible = _isErrorVisible.asStateFlow()

    private val _isContentVisible = MutableStateFlow<Boolean?>(null)
    val isContentVisible = _isContentVisible.asStateFlow()

    private val _contentFlow = MutableStateFlow<FilmDetailedModel?>(null)
    val contentFlow = _contentFlow.asStateFlow()

    private suspend fun loadContent (filmId: Int){
        val filmResponse = FilmsRepository().getFilmDetailed(filmId)
        Log.i("filmResponse", "$filmResponse")
        val filmContent = FilmDetailedModel(
            filmResponse.nameRu,
            filmResponse.posterUrl,
            filmResponse.genres?.joinToString(),
            filmResponse.countries?.joinToString(),
            filmResponse.description?.toString(),
        )
        _contentFlow.value = filmContent
    }

    suspend fun getContent(filmId: Int){
        _isContentVisible.value = false
        _isErrorVisible.value = false
        _isProgressBarVisible.value = true
        try {
            loadContent(filmId)
            _isContentVisible.value = true
            _isErrorVisible.value = false
            _isProgressBarVisible.value = false
        } catch (e: Exception){
            Log.e("Request Error", "$e")
            _isContentVisible.value = false
            _isErrorVisible.value = true
            _isProgressBarVisible.value = false
        }

    }

}