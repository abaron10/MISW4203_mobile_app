package com.example.vinilos.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.addTextChangedListener
import com.example.vinilos.R
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CreateAlbumFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateAlbumFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var selectedGenre: String? = null
    private lateinit var albumName: EditText
    private lateinit var coverUrl: EditText
    private lateinit var releaseDate: EditText
    private lateinit var albumDescription: EditText
    private lateinit var genre: AutoCompleteTextView
    private lateinit var recordLabel: EditText
    private lateinit var addAlbum: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
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
        setBehaviorEmptyEditText(recordLabel, "Record label")

        addAlbum = view.findViewById(R.id.btn_add_album)
        setAddAlbumBehavior()
    }

    private fun setBehaviorEmptyEditText(editText: EditText, name: String){
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(TextUtils.isEmpty(editText.text)){
                    editText.error = "$name cannot be empty"
                }
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
                if(TextUtils.isEmpty(albumName.text)){
                    albumName.error = "Image url cannot be empty"
                }
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
            private val ddmmyyyy = "DDMMYYYY"
            private val cal = Calendar.getInstance()
            private val validDateRegex = "^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}\$".toRegex()

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

                        mon = if (mon < 1) 1 else if (mon > 12) 12 else mon
                        cal.set(Calendar.MONTH, mon - 1)
                        year = if (year < 1900) 1900 else if (year > 2100) 2100 else year
                        cal.set(Calendar.YEAR, year)

                        day = if (day > cal.getActualMaximum(Calendar.DATE)) cal.getActualMaximum(Calendar.DATE) else day
                        clean = String.format("%02d%02d%02d", day, mon, year)
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                        clean.substring(2, 4),
                        clean.substring(4, 8))

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
        val items = listOf("Electronic", "Hip hop", "Pop", "Rock", "Salsa")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        genre.setAdapter(adapter)
        genre.setOnItemClickListener { parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
            selectedGenre = parent?.getItemAtPosition(position).toString();
        }
    }

    private fun setAddAlbumBehavior(){
        addAlbum.setOnClickListener{
            val fields: List<EditText> = listOf(albumName, coverUrl, releaseDate, albumDescription, genre, recordLabel)
            val results: MutableList<Boolean> = mutableListOf()
            for (f in fields){
                results.add(hasError(f))
            }

           if(results.all { it }){
               println("LISTO PARA CREAR ALBUM")
           }
        }
    }

    private fun hasError(editText: EditText): Boolean {
        if (TextUtils.isEmpty(editText.error)){
            return true
        }
        return false
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CreateAlbumFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CreateAlbumFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}