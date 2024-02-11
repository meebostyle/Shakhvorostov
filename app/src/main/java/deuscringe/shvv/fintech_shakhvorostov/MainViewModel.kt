package deuscringe.shvv.fintech_shakhvorostov

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import deuscringe.shvv.fintech_shakhvorostov.adapters.FilmModel

class MainViewModel:ViewModel() {
    val liveDataJSON = MutableLiveData<FilmModel>()
}