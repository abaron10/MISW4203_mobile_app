package com.example.vinilos.network

import android.content.Context
import com.android.volley.Response
import com.android.volley.VolleyError
import com.example.vinilos.models.Collector
import org.json.JSONArray

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

    fun getCollectors(onComplete:(resp:List<Collector>)->Unit, onError: (error: VolleyError)->Unit){
        requestQueue.add(getRequest("collectors",  { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Collector>()
                for (i in 0 until resp.length()) {
                    val item = resp.getJSONObject(i)
                    list.add(i, Collector(collectorId = item.getInt("id"),name = item.getString("name"), telephone = item.getString("telephone"), email = item.getString("email")))
                }
                onComplete(list)
            },
            {
                onError(it)
            }))
    }
}