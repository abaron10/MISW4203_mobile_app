package com.example.vinilos.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vinilos.R

private const val ALBUM_ID_PARAM = "albumId"


class AddTrackFragment : Fragment() {
    private var albumId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            albumId = it.getInt(ALBUM_ID_PARAM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_track, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(albumId: Int) =
            AddTrackFragment().apply {
                arguments = Bundle().apply {
                    putInt(ALBUM_ID_PARAM, albumId)
                }
            }
    }
}