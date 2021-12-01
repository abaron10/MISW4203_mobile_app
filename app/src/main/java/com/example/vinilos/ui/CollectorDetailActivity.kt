package com.example.vinilos.ui

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.vinilos.R
import com.example.vinilos.databinding.ActivityCollectorDetailBinding
import com.example.vinilos.models.Collector
import com.example.vinilos.viewmodels.CollectorDetailViewModel

class CollectorDetailActivity : BaseActivity() {
    private var collectorId: Int = 0
    private var _binding: ActivityCollectorDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CollectorDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCollectorDetailBinding.inflate(layoutInflater)
        binding.setLifecycleOwner(this)
        setContentView(binding.root)
        title = ""
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        this.collectorId = intent.getIntExtra(
            BaseActivity.INTENT_EXTRA_COLLECTOR_ID, 0
        )

        viewModel = ViewModelProvider(
            this,
            CollectorDetailViewModel.Factory(application, this.collectorId)).get(
            CollectorDetailViewModel::class.java
        )
        binding.also {
            it.viewModel = viewModel
        }
        viewModel.collector.observe(this, Observer<Collector> {
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