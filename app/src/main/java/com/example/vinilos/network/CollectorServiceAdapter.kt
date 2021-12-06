package com.example.vinilos.network

import android.content.Context
import com.example.vinilos.models.Collector
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class CollectorServiceAdapter constructor(context: Context): NetworkServiceAdapter(context)  {
    companion object{
        var instance: CollectorServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: CollectorServiceAdapter(context).also {
                    instance = it
                }
            }
    }

    suspend fun getCollectors() = suspendCoroutine<List<Collector>>{ cont ->
        requestQueue.add(
            getRequest("collectors",  { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Collector>()
                var item: JSONObject
                for (i in 0 until resp.length()) {
                    item = resp.getJSONObject(i)
                    list.add(i, Collector(collectorId = item.getInt("id"),name = item.getString("name"), telephone = item.getString("telephone"), email = item.getString("email"), createdAt = i.toLong()))
                }
                cont.resume(list)
            }, {
                cont.resumeWithException(it)
            })
        )
    }

    suspend fun getCollector(collectorId: Int) = suspendCoroutine<Collector> { cont ->
        requestQueue.add(
            getRequest("collectors/$collectorId",  { response ->
                val item = JSONObject(response)
                val collector = Collector(collectorId = item.getInt("id"),name = item.getString("name"), telephone = item.getString("telephone"), email = item.getString("email"))
                cont.resume(collector)
            }, {
                cont.resumeWithException(it)
            })
        )
    }
}