package com.example.vinilos.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.vinilos.models.Album
import com.example.vinilos.models.Artist
import com.example.vinilos.repositories.ArtistRepository

class ArtistViewModel(application: Application): AndroidViewModel(application) {
    private val artistRepository = ArtistRepository(application)
    private val _artist = MutableLiveData<List<Artist>>()
    private var initialArtist: List<Artist> = emptyList()
    val artist: LiveData<List<Artist>>
        get() = _artist
    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown
    private val _isLoading = MutableLiveData<Boolean>(true)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    init {
        refreshDataFromNetwork()
    }

    fun refreshDataFromNetwork() {
        _isLoading.value = true
        _isNetworkErrorShown.value = false
        artistRepository.refreshData({
            _artist.postValue(it)
            initialArtist = it
            _isLoading.value = false
        },{
            _isNetworkErrorShown.value = true
            _isLoading.value = false
        })
    }

    fun filterByArtistName(name: String) {
        var filteredList = mutableListOf<Artist>()
        for(artist in this.initialArtist) {
            if(artist.name.lowercase().startsWith(name.lowercase())) {
                filteredList.add(artist)
            }
        }
        _artist.postValue(filteredList)
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ArtistViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ArtistViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}