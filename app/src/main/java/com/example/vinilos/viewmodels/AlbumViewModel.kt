package com.example.vinilos.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.vinilos.models.Album
import com.example.vinilos.repositories.AlbumRepository

class AlbumViewModel(application: Application, albumId: Int): AndroidViewModel(application) {
    private val albumRepository = AlbumRepository(application)
    private val id = albumId
    private val _album = MutableLiveData<Album>()
    val album: LiveData<Album>
        get() = _album
    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown
    private val _isLoading = MutableLiveData<Boolean>(true)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    init {
        refreshDateFromNetwork()
    }

    fun refreshDateFromNetwork() {
        _isLoading.value = true
        _isNetworkErrorShown.value = false
        albumRepository.refreshAlbum(id, {
            _album.postValue(it)
            _isLoading.value = false
        }, {
            _isNetworkErrorShown.value = true
            _isLoading.value = false
        })
    }

    class Factory(val app: Application, val albumId: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AlbumViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AlbumViewModel(app, albumId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}