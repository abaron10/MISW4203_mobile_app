package com.example.vinilos.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.vinilos.models.Album
import com.example.vinilos.repositories.AlbumRepository

class AlbumViewModel(application: Application): AndroidViewModel(application) {
    private val albumsRepository = AlbumRepository(application)
    private val _albums = MutableLiveData<List<Album>>()
    private var initialAlbums: List<Album> = emptyList()
    val albums: LiveData<List<Album>>
        get() = _albums
    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError
    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown
    private val _isLoading = MutableLiveData<Boolean>(true)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        //_isLoading.value = true
        albumsRepository.refreshData({
            _albums.postValue(it)
            initialAlbums = it
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
            _isLoading.value = false
        },{
            _eventNetworkError.value = true
            _isLoading.value = false
        })
    }

    fun filterByAlbumName(name: String) {
        var filteredList = mutableListOf<Album>()
        for(album in this.initialAlbums) {
            if(album.name.lowercase().startsWith(name.lowercase())) {
                filteredList.add(album)
            }
        }
        _albums.postValue(filteredList)
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}