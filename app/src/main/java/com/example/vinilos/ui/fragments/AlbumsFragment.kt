package com.example.vinilos.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.R
import com.example.vinilos.adapters.AlbumsAdapter
import com.example.vinilos.models.Album

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AlbumsFragment : Fragment() {
    private lateinit var albumsAdapter: AlbumsAdapter
    private var dataList = mutableListOf<Album>()
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_albums, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(activity!!.applicationContext,2)
        albumsAdapter = AlbumsAdapter(activity!!.applicationContext)
        recyclerView.adapter = albumsAdapter

        dataList.add(Album(1,"a","a","a","a","a","a"))
        dataList.add(Album(1,"a","a","a","a","a","a"))
        dataList.add(Album(1,"a","a","a","a","a","a"))
        dataList.add(Album(1,"a","a","a","a","a","a"))
        dataList.add(Album(1,"a","a","a","a","a","a"))
        albumsAdapter.setDataList(dataList)
        return view
    }
}