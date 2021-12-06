package com.example.vinilos.adapters

import android.annotation.SuppressLint
import com.example.vinilos.R
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.vinilos.databinding.CollectorItemLayoutBinding
import com.example.vinilos.models.Collector
import com.example.vinilos.ui.BaseActivity
import com.example.vinilos.ui.CollectorDetailActivity

class CollectorsAdapter(var context: Context) : RecyclerView.Adapter<CollectorsAdapter.CollectorViewHolder>() {

  var collectors :List<Collector> = emptyList()
    @SuppressLint("NotifyDataSetChanged")
    set(value) {
      field = value
      notifyDataSetChanged()
    }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectorViewHolder {
    val withDataBinding: CollectorItemLayoutBinding = DataBindingUtil.inflate(
      LayoutInflater.from(parent.context),
      CollectorViewHolder.LAYOUT,
      parent,
      false)
    return CollectorViewHolder(withDataBinding)
  }

  override fun onBindViewHolder(holder: CollectorViewHolder, position: Int) {
    holder.viewDataBinding.also {
      it.collector = collectors[position]
    }
    holder.viewDataBinding.root.setOnClickListener {
      Toast.makeText(it.context, collectors[position].name, Toast.LENGTH_SHORT).show()
    }
    holder.viewDataBinding.root.setOnClickListener {
      val intent = Intent(context, CollectorDetailActivity::class.java)
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
      intent.putExtra(
        BaseActivity.INTENT_EXTRA_COLLECTOR_ID, collectors[position].collectorId
      )
      context.startActivity(intent)
    }
  }

  override fun getItemCount(): Int {
    return collectors.size
  }

  class CollectorViewHolder(val viewDataBinding: CollectorItemLayoutBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
      @LayoutRes
      val LAYOUT = R.layout.collector_item_layout
    }
  }
}