package com.example.vinilos.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.vinilos.models.Album
import com.example.vinilos.repositories.AlbumRepository

class AlbumsViewModel(application: Application): AndroidViewModel(application) {
    private val albumsRepository = AlbumRepository(application)
    var isUser: Boolean = true
    private val _albums = MutableLiveData<List<Album>>()
    private var initialAlbums: List<Album> = emptyList()
    val albums: LiveData<List<Album>>
        get() = _albums
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
        albumsRepository.refreshData({
            _albums.postValue(it)
            initialAlbums = it
            _isLoading.value = false
        },{
            _isNetworkErrorShown.value = true
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

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumsViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}