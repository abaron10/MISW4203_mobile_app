package com.example.vinilos.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.example.vinilos.database.VinylRoomDatabase
import com.example.vinilos.models.Collector
import com.example.vinilos.network.CollectorServiceAdapter

class CollectorRepository (val application: Application){
    suspend fun refreshData(): List<Collector> {
        val db = VinylRoomDatabase.getDatabase(application.applicationContext)
        val cached = db.collectorDao().getCollectors()
        return if(cached.isNullOrEmpty()) {
            val collectors = CollectorServiceAdapter.getInstance(application).getCollectors()
            for(collector in collectors) {
                db.collectorDao().insert(collector)
            }
            collectors
        } else {
            cached
        }
    }
}