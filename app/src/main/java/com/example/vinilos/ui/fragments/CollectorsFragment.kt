package com.example.vinilos.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.adapters.CollectorsAdapter
import com.example.vinilos.databinding.FragmentCollectorsBinding
import com.example.vinilos.models.Collector
import com.example.vinilos.viewmodels.CollectorViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText


class CollectorsFragment : Fragment() {
    private var _binding: FragmentCollectorsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CollectorViewModel

    private lateinit var collectorsAdapter: CollectorsAdapter
    private lateinit var collectorRecyclerView: RecyclerView
    private lateinit var collectorInputText: TextInputEditText
    private lateinit var btnTryAgain: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCollectorsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        val view = binding.root
        collectorsAdapter = CollectorsAdapter(requireActivity().applicationContext)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        collectorRecyclerView = binding.collectorRecyclerView
        collectorRecyclerView.layoutManager = LinearLayoutManager(requireActivity().applicationContext)
        collectorRecyclerView.adapter = collectorsAdapter
        collectorInputText = binding.searchBoxField
        btnTryAgain = binding.btnTryAgain
        collectorInputText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                viewModel.filterByCollectorName(s.toString())
            }
        })

        btnTryAgain.setOnClickListener {
            viewModel.refreshDataFromNetwork()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity)
        viewModel = ViewModelProvider(this, CollectorViewModel.Factory(activity.application)).get(CollectorViewModel::class.java)
        binding.also {
            it.viewModel = viewModel
        }
        viewModel.collectors.observe(viewLifecycleOwner, Observer<List<Collector>> {
            it.apply {
                collectorsAdapter.collectors = this
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}