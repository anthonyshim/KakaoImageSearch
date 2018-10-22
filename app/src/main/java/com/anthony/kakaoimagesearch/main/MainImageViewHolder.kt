package com.anthony.kakaoimagesearch.main

import com.anthony.kakaoimagesearch.common.BaseViewHolder
import com.anthony.kakaoimagesearch.databinding.ItemMainImageBinding
import com.anthony.kakaoimagesearch.model.Document

class MainImageViewHolder(private val binding: ItemMainImageBinding) : BaseViewHolder<Document>(binding.root) {
    override fun bind(item: Document) {
        binding.item = item
    }
}