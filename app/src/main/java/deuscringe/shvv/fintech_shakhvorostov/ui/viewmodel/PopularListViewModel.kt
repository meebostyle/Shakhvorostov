package deuscringe.shvv.fintech_shakhvorostov.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import deuscringe.shvv.fintech_shakhvorostov.data.FilmsRepository

import deuscringe.shvv.fintech_shakhvorostov.ui.model.FilmModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PopularListViewModel: ViewModel() {

    private val filmsRepository = FilmsRepository()

    private val _filmsFlow = MutableStateFlow<List<FilmModel>?> (null)
    val filmsFlow = _filmsFlow.asStateFlow()

    private val _isProgressBarVisible = MutableStateFlow<Boolean>(true)
    val isProgressBarVisible = _isProgressBarVisible.asStateFlow()

    private val _isRecyclerVisible = MutableStateFlow<Boolean>(false)
    val isRecyclerVisible = _isRecyclerVisible.asStateFlow()

    private val _isErrorVisible = MutableStateFlow<Boolean>(false)
    val isErrorVisible = _isErrorVisible.asStateFlow()



    private suspend fun loadFilms() {
        val filmResponse = filmsRepository.getFilmsCollection()
        val films = filmResponse.films.map {films ->
            FilmModel(
                films.filmId,
                films.nameRu,
                "${films.genres.get(0).genre} (${films.year})",
                films.posterUrlPreview
            )
        }
        _filmsFlow.value = films
    }

    suspend fun getFilms (){
        _isProgressBarVisible.value = true
        _isErrorVisible.value = false
        _isRecyclerVisible.value = false
        try {
            loadFilms()
            _isRecyclerVisible.value = true
        } catch (e: Exception){
            _isErrorVisible.value = true
            Log.i("ERROR", "$e")
        }

        _isProgressBarVisible.value = false
    }

    fun addToFavorite(index: Int) {
        _filmsFlow.value?.let { currentList ->
            val updatedList = currentList.toMutableList().apply {
                this[index] = this[index].copy(isFavorite = !this[index].isFavorite)
            }
            _filmsFlow.value = updatedList
        }
    }

}