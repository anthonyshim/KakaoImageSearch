package com.anthony.kakaoimagesearch.main.detail

import android.databinding.ObservableField
import com.anthony.kakaoimagesearch.model.Document

class DetailViewModel(document: Document) {
    var item = ObservableField<Document>(document)
}