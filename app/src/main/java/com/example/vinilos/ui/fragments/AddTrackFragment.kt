package com.example.vinilos.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.vinilos.R
import java.util.*

private const val ALBUM_ID_PARAM = "albumId"


class AddTrackFragment : Fragment() {
    private var albumId: Int? = null
    private lateinit var trackDuration: EditText


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        trackDuration = view.findViewById(R.id.track_duration)
        setBehaviorTrackDuration()
    }

    private fun setBehaviorTrackDuration() {
        trackDuration.addTextChangedListener(object : TextWatcher {

            private var current = ""
            private val mmss = "mmss"

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString() != current) {
                    var clean = p0.toString().replace("[^\\d.]|\\.".toRegex(), "")
                    val cleanC = current.replace("[^\\d.]|\\.".toRegex(), "")

                    val cl = clean.length
                    var sel = cl
                    var i = 2
                    while (i <= cl && i < 4) {
                        sel++
                        i += 2
                    }
                    if (clean == cleanC) sel--

                    if (clean.length < 4) {
                        clean += mmss.substring(clean.length)
                    } else {
                        var minutes = Integer.parseInt(clean.substring(0, 2))
                        var seconds = Integer.parseInt(clean.substring(2, 4))

                        minutes = if (minutes > 60) 60 else if (minutes < 0) 0 else minutes
                        seconds = if (seconds > 60) 60 else if (seconds < 0) 0 else seconds

                        clean = String.format("%02d%02d", minutes, seconds)
                    }

                    if (clean.length < 4) {
                        clean += mmss.substring(clean.length)
                    } else {
                        clean = String.format("%s:%s", clean.substring(0, 2),
                            clean.substring(2, 4))
                    }


                    sel = if (sel < 0) 0 else sel
                    current = clean
                    trackDuration.setText(current)
                    trackDuration.setSelection(if (sel < current.count()) sel else current.count())
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable) {
                /*
                if(!validDateRegex.matches(current)){
                    releaseDate.error = "Date cannot be empty"
                }
                */
            }
        })
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