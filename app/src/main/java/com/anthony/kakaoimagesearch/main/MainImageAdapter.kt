package com.anthony.kakaoimagesearch.main

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.anthony.kakaoimagesearch.R
import com.anthony.kakaoimagesearch.common.BaseRecyclerViewAdapter
import com.anthony.kakaoimagesearch.model.Document

class MainImageAdapter(onItemClickListener: OnItemClickListener) : BaseRecyclerViewAdapter<Document, MainImageViewHolder>() {
    init {
        this.onItemClickListener = onItemClickListener
    }

    override fun onBindView(holder: MainImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MainImageViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_main_image, parent, false))
    }
}