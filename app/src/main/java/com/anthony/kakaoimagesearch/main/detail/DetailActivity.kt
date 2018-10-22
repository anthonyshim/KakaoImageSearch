package com.anthony.kakaoimagesearch.main.detail

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.anthony.kakaoimagesearch.R
import com.anthony.kakaoimagesearch.databinding.ActivityDetailBinding
import com.anthony.kakaoimagesearch.model.Document

class DetailActivity : AppCompatActivity() {
    companion object {
        private const val EXTRA_DOCUMENT = "document"

        fun startActivity(context: Context, document: Document) {
            context.startActivity(Intent(context, DetailActivity::class.java).apply {
                putExtra(EXTRA_DOCUMENT, document)
            })
        }
    }

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView<ActivityDetailBinding>(this@DetailActivity, R.layout.activity_detail).apply {
            intent.getParcelableExtra<Document>(EXTRA_DOCUMENT)?.let {
                viewModel = DetailViewModel(intent.getParcelableExtra(EXTRA_DOCUMENT))

            }
        }
    }
}