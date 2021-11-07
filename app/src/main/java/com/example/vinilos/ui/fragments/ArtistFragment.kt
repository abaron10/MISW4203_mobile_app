package com.example.vinilos.ui.fragments

import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.vinilos.adapters.ArtistAdapter
import com.example.vinilos.databinding.FragmentArtistsBinding
import com.example.vinilos.models.Artist
import com.example.vinilos.viewmodels.ArtistViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.TextInputEditText


class ArtistFragment : Fragment() {
    private var _binding: FragmentArtistsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ArtistViewModel

    private lateinit var artistAdapter: ArtistAdapter
    private lateinit var artistRecyclerView: RecyclerView
    private lateinit var artistInputText: TextInputEditText
    private lateinit var btnTryAgain: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArtistsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        val view = binding.root
        artistAdapter = ArtistAdapter(activity!!.applicationContext)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        artistRecyclerView = binding.artistRecyclerView
        artistRecyclerView.layoutManager = GridLayoutManager(activity!!.applicationContext,2)
        artistRecyclerView.adapter = artistAdapter
        artistInputText = binding.searchBoxField
        btnTryAgain = binding.btnTryAgain
        artistInputText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                viewModel.filterByArtistName(s.toString())
            }
        })

        btnTryAgain.setOnClickListener {
            viewModel.refreshDataFromNetwork()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity)
        viewModel = ViewModelProvider(this, ArtistViewModel.Factory(activity.application)).get(ArtistViewModel::class.java)
        binding.also {
            it.viewModel = viewModel
        }
        viewModel.artist.observe(viewLifecycleOwner, Observer<List<Artist>> {
            it.apply {
                artistAdapter.artist = this
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}