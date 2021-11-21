package com.example.vinilos.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.vinilos.models.Collector
import com.example.vinilos.repositories.CollectorRepository

class CollectorViewModel(application: Application): AndroidViewModel(application) {
    private val collectorsRepository = CollectorRepository(application)
    private val _collectors = MutableLiveData<List<Collector>>()
    private var initialCollectors: List<Collector> = emptyList()
    val collectors: LiveData<List<Collector>>
        get() = _collectors
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
        collectorsRepository.refreshData({
            _collectors.postValue(it)
            initialCollectors = it
            _isLoading.value = false
        },{
            _isNetworkErrorShown.value = true
            _isLoading.value = false
        })
    }

    fun filterByCollectorName(name: String) {
        var filteredList = mutableListOf<Collector>()
        for(coll in this.initialCollectors) {
            if(coll.name.lowercase().startsWith(name.lowercase())) {
                filteredList.add(coll)
            }
        }
        _collectors.postValue(filteredList)
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CollectorViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CollectorViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}