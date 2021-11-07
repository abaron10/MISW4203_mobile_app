package com.example.vinilos.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.vinilos.events.SingleLiveEvent
import com.example.vinilos.models.Album
import com.example.vinilos.repositories.AlbumRepository

class CreateAlbumViewModel(application: Application): AndroidViewModel(application) {
    private val albumsRepository = AlbumRepository(application)
    val wasSuccessful = SingleLiveEvent<Boolean>()


    fun addNewAlbum(albumParams: Map<String, String>) {
        albumsRepository.addAlbum(albumParams, {
            println("LAURA "+it.albumId)
            wasSuccessful.value = true
        },{
            wasSuccessful.value = false
        })
    }


    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CreateAlbumViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CreateAlbumViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}