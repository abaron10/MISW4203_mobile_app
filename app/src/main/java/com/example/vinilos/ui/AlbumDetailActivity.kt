package com.example.vinilos.ui

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.databinding.BindingAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.vinilos.R
import com.example.vinilos.databinding.ActivityAlbumDetailBinding
import com.example.vinilos.databinding.ActivityAlbumDetailBindingImpl
import com.example.vinilos.models.Album
import com.example.vinilos.repositories.AlbumRepository
import com.example.vinilos.viewmodels.AlbumViewModel
import com.example.vinilos.viewmodels.AlbumsViewModel
import com.google.android.material.imageview.ShapeableImageView

class AlbumDetailActivity : AppCompatActivity() {
    private var albumId: Int = 0
    private var _binding: ActivityAlbumDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AlbumViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAlbumDetailBinding.inflate(layoutInflater)
        binding.setLifecycleOwner(this)
        setContentView(binding.root)
        title = ""
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        this.albumId = intent.getIntExtra(
            BaseActivity.INTENT_EXTRA_ALBUM_ID, 0
        )
        viewModel = ViewModelProvider(
            this,
            AlbumViewModel.Factory(application, this.albumId)).get(
            AlbumViewModel::class.java
        )
        binding.also {
            it.viewModel = viewModel
        }
        viewModel.album.observe(this, Observer<Album> {
            it.apply {
                title = this.name
            }
        })
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