package com.example.vinilos.ui

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.vinilos.R
import com.example.vinilos.databinding.ActivityArtistDetailBinding
import com.example.vinilos.models.Artist
import com.example.vinilos.viewmodels.ArtistViewModel

class ArtistDetailActivity : BaseActivity() {
    private var artistId: Int = 0
    private var _binding: ActivityArtistDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ArtistViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityArtistDetailBinding.inflate(layoutInflater)
        binding.setLifecycleOwner(this)
        setContentView(binding.root)
        title = ""
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        this.artistId = intent.getIntExtra(
            BaseActivity.INTENT_EXTRA_ARTIST_ID, 0
        )

        viewModel = ViewModelProvider(
            this,
            ArtistViewModel.Factory(application, this.artistId)).get(
            ArtistViewModel::class.java
        )
        binding.also {
            it.viewModel = viewModel
        }
        viewModel.artist.observe(this, Observer<Artist> {})
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}