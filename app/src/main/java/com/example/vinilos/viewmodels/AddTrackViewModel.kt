package com.example.vinilos.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.vinilos.events.SingleLiveEvent
import com.example.vinilos.repositories.TrackRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddTrackViewModel(application: Application): AndroidViewModel(application) {
    private val trackRepository = TrackRepository(application)
    val wasSuccessful = SingleLiveEvent<Boolean>()

    fun addNewTrack(trackParams: Map<String, String>, albumId: Int) {
        viewModelScope.launch(Dispatchers.Default) {
            withContext(Dispatchers.IO){
                try {
                    trackRepository.addTrack(trackParams, albumId)
                    wasSuccessful.postValue(true)
                } catch(e: Exception) {
                    wasSuccessful.postValue(false)
                }
            }
        }
    }


    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AddTrackViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AddTrackViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}