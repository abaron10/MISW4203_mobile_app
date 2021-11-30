package com.example.vinilos.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.ArrayMap
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.vinilos.R
import com.example.vinilos.viewmodels.AddTrackViewModel

private const val ALBUM_ID_PARAM = "albumId"


class AddTrackFragment : Fragment() {
    private var albumId: Int? = null
    private lateinit var trackDuration: EditText
    private lateinit var trackName: EditText
    private lateinit var addTrackButton: Button
    private val validDurationRegex = "^(([0-5][0-9])|[0-6]0)(\\:)([0-5][0-9])\$".toRegex()

    private lateinit var viewModel: AddTrackViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity)
        viewModel = ViewModelProvider(this, AddTrackViewModel.Factory(activity.application)).get(
            AddTrackViewModel::class.java)

        viewModel.wasSuccessful.observe(viewLifecycleOwner, {
            if (it == true) {
                Toast.makeText(activity.applicationContext, "Track was created successfully", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(activity.applicationContext, "There was an error creating the track", Toast.LENGTH_LONG).show()
            }
            this.activity?.onBackPressed()
        })
    }

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
        trackName = view.findViewById(R.id.track_name)
        setBehaviorEmptyEditText(trackName, "Track name")

        trackDuration = view.findViewById(R.id.track_duration)
        setBehaviorTrackDuration()

        addTrackButton = view.findViewById(R.id.btn_add_track)
        setAddTrackBehavior()
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
                if(!validDurationRegex.matches(current)){
                    trackDuration.error = "Track duration cannot be empty"
                }
            }
        })
    }

    private fun setBehaviorEmptyEditText(editText: EditText, name: String){
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                checkEmptyField(editText, name)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun setAddTrackBehavior(){
        addTrackButton.setOnClickListener{
            checkEmptyField(trackName, "Track name")
            checkTrackDuration()

            val fields: List<EditText> = listOf(trackName, trackDuration)
            val results: MutableList<Boolean> = mutableListOf()
            for (f in fields){
                results.add(hasError(f))
            }

            if(results.all { it }){
                val trackParams: ArrayMap<String, String> = ArrayMap()
                trackParams["name"] = trackName.text.toString()
                trackParams["duration"] = trackDuration.text.toString()

                this.albumId?.let { albumId -> viewModel.addNewTrack(trackParams, albumId) }
            } else {
                Toast.makeText(it.context, "Could not create album. Check input", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkEmptyField(editText: EditText, name: String){
        if(TextUtils.isEmpty(editText.text)){
            editText.error = "$name cannot be empty"
        }
    }

    private fun checkTrackDuration(){
        if(!validDurationRegex.matches(trackDuration.text)){
            trackDuration.error = "Track duration cannot be empty"
        }
    }

    private fun hasError(editText: EditText): Boolean {
        if (TextUtils.isEmpty(editText.error)){
            return true
        }
        return false
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