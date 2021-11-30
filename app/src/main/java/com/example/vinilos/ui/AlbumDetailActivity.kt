package com.example.vinilos.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.R
import com.example.vinilos.adapters.AlbumTracksAdapter
import com.example.vinilos.databinding.ActivityAlbumDetailBinding
import com.example.vinilos.models.Album
import com.example.vinilos.ui.fragments.AddTrackFragment
import com.example.vinilos.viewmodels.AlbumViewModel

class AlbumDetailActivity : AppCompatActivity() {
    private var albumId: Int = 0
    private var _binding: ActivityAlbumDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AlbumViewModel

    private lateinit var tracksAdapter: AlbumTracksAdapter
    private lateinit var tracksRecyclerView: RecyclerView
    private lateinit var addTrackButton: Button


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
        tracksAdapter = AlbumTracksAdapter(applicationContext)
        tracksRecyclerView = binding.albumDetailTracksRecyclerView
        tracksRecyclerView.isNestedScrollingEnabled = false
        tracksRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        tracksRecyclerView.adapter = tracksAdapter

        val retryView = binding.retryView
        val btnTryAgain = retryView.btnTryAgain
        btnTryAgain.setOnClickListener {
            viewModel.refreshDataFromNetwork()
        }

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
                tracksAdapter.tracks = this.tracks
            }
        })

        addTrackButton = findViewById(R.id.album_add_track)
        addTrackButton.setOnClickListener {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.constraint_layout, AddTrackFragment.newInstance(this.albumId))
            transaction.disallowAddToBackStack()
            transaction.commit()
        }
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