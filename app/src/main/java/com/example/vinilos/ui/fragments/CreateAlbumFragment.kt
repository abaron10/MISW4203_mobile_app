package com.example.vinilos.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.ArrayMap
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.example.vinilos.R
import com.example.vinilos.viewmodels.CreateAlbumViewModel
import java.util.*


class CreateAlbumFragment : Fragment() {
    private var selectedGenre: String = ""
    private var selectedRecordLabel: String = ""
    private val validDateRegex = "^(((0)[0-9])|((1)[0-2]))(\\/)([0-2][0-9]|(3)[0-1])(\\/)\\d{4}\$".toRegex()

    private lateinit var viewModel: CreateAlbumViewModel
    private lateinit var albumName: EditText
    private lateinit var coverUrl: EditText
    private lateinit var releaseDate: EditText
    private lateinit var albumDescription: EditText
    private lateinit var genre: AutoCompleteTextView
    private lateinit var recordLabel: AutoCompleteTextView
    private lateinit var addAlbum: Button

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity)
        viewModel = ViewModelProvider(this, CreateAlbumViewModel.Factory(activity.application)).get(
            CreateAlbumViewModel::class.java)

        viewModel.wasSuccessful.observe(viewLifecycleOwner, {
            if (it == true) {
                Toast.makeText(activity.applicationContext, "Album was created successfully", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(activity.applicationContext, "There was an error creating the album", Toast.LENGTH_LONG).show()
            }
            this.activity?.onBackPressed()
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_album, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        albumName = view.findViewById(R.id.album_name)
        setBehaviorEmptyEditText(albumName, "Album name")

        coverUrl = view.findViewById(R.id.cover_image_url)
        setBehaviorCoverUrl()

        releaseDate = view.findViewById(R.id.release_date)
        setBehaviorReleaseDate()

        albumDescription = view.findViewById(R.id.album_description)
        setBehaviorEmptyEditText(albumDescription, "Album description")

        genre = view.findViewById(R.id.genre)
        setBehaviorGenre()

        recordLabel = view.findViewById(R.id.record_label)
        setBehaviorRecordLabel()

        addAlbum = view.findViewById(R.id.btn_add_album)
        setAddAlbumBehavior()
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

    private fun setBehaviorCoverUrl(){
        coverUrl.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                checkEmptyField(coverUrl, "Image url")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!Patterns.WEB_URL.matcher(coverUrl.text.toString()).matches()) {
                    coverUrl.error = "Invalid Url"
                }
            }
        })
    }

    private fun setBehaviorReleaseDate() {
        releaseDate.addTextChangedListener(object : TextWatcher {

            private var current = ""
            private val ddmmyyyy = "MMDDYYYY"
            private val cal = Calendar.getInstance()

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString() != current) {
                    var clean = p0.toString().replace("[^\\d.]|\\.".toRegex(), "")
                    val cleanC = current.replace("[^\\d.]|\\.".toRegex(), "")

                    val cl = clean.length
                    var sel = cl
                    var i = 2
                    while (i <= cl && i < 6) {
                        sel++
                        i += 2
                    }
                    if (clean == cleanC) sel--

                    if (clean.length < 8) {
                        clean += ddmmyyyy.substring(clean.length)
                    } else {
                        var day = Integer.parseInt(clean.substring(0, 2))
                        var mon = Integer.parseInt(clean.substring(2, 4))
                        var year = Integer.parseInt(clean.substring(4, 8))

                        val currentMon = Calendar.getInstance().get(Calendar.MONTH) + 1
                        mon = if (mon > currentMon) currentMon else if (mon < 1) currentMon else if (mon > 12) currentMon else mon
                        cal.set(Calendar.MONTH, mon)
                        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
                        year = if (year > currentYear) currentYear else if (year < 1) currentYear else year
                        cal.set(Calendar.YEAR, year)

                        val currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

                        day = if (day > cal.getActualMaximum(Calendar.DATE)) currentDay else if (day > currentDay) currentDay else if (day < 1) currentDay else day
                        clean = String.format("%02d%02d%02d", mon, day, year)
                    }

                    if (clean.length < 8) {
                        clean += ddmmyyyy.substring(clean.length)
                    } else {
                        clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8))
                    }


                    sel = if (sel < 0) 0 else sel
                    current = clean
                    releaseDate.setText(current)
                    releaseDate.setSelection(if (sel < current.count()) sel else current.count())
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable) {
                if(!validDateRegex.matches(current)){
                    releaseDate.error = "Date cannot be empty"
                }
            }
        })
    }

    private fun setBehaviorGenre(){
        val items = listOf("Classical", "Folk", "Rock", "Salsa")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        genre.setAdapter(adapter)
        genre.setOnItemClickListener { parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
            selectedGenre = parent?.getItemAtPosition(position).toString()
            genre.error = null
        }
    }

    private fun setBehaviorRecordLabel(){
        val items = listOf("Discos Fuentes", "Elektra", "EMI", "Fania Records", "Sony Music")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        recordLabel.setAdapter(adapter)
        recordLabel.setOnItemClickListener { parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
            selectedRecordLabel = parent?.getItemAtPosition(position).toString()
            recordLabel.error = null
        }
    }

    private fun setAddAlbumBehavior(){
        addAlbum.setOnClickListener{
            checkFields()
            val fields: List<EditText> = listOf(albumName, coverUrl, releaseDate, albumDescription, genre, recordLabel)
            val results: MutableList<Boolean> = mutableListOf()
            for (f in fields){
                results.add(hasError(f))
            }

           if(results.all { it }){
               val albumParams: ArrayMap<String, String> = ArrayMap()
               albumParams["name"] = albumName.text.toString()
               albumParams["cover"] = coverUrl.text.toString()
               albumParams["releaseDate"] = releaseDate.text.toString()
               albumParams["description"] = albumDescription.text.toString()
               albumParams["genre"] = selectedGenre
               albumParams["recordLabel"] = recordLabel.text.toString()
               viewModel.addNewAlbum(albumParams)
           } else {
               Toast.makeText(it.context, "Could not create album. Check input", Toast.LENGTH_SHORT).show()
           }
        }
    }

    private fun hasError(editText: EditText): Boolean {
        if (TextUtils.isEmpty(editText.error)){
            return true
        }
        return false
    }

    private fun checkFields(){
        val fields: List<EditText> = listOf(albumName, coverUrl, albumDescription)
        for(f in fields){
            checkEmptyField(f, f.hint.toString())
        }
        checkReleaseDate()
        checkSelectedOptions()
    }

    private fun checkEmptyField(editText: EditText, name: String){
        if(TextUtils.isEmpty(editText.text)){
            editText.error = "$name cannot be empty"
        }
    }

    private fun checkReleaseDate(){
        if(!validDateRegex.matches(releaseDate.text)){
            releaseDate.error = "Date cannot be empty"
        }
    }

    private fun checkSelectedOptions(){
        if(selectedGenre == ""){
            genre.error = "Genre was not selected"
        }
        if(selectedRecordLabel == ""){
            recordLabel.error = "Record label was not selected"
        }
    }

}