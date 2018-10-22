package com.anthony.kakaoimagesearch.common

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.anthony.kakaoimagesearch.R
import com.anthony.kakaoimagesearch.main.MainImageAdapter
import com.anthony.kakaoimagesearch.model.Document
import com.facebook.drawee.view.SimpleDraweeView

object DataBindingComponets {
    @JvmStatic
    @BindingAdapter("singleClickListener")
    fun setSingleClickListener(view: View, onClickListener: View.OnClickListener) {
        view.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(v: View) {
                onClickListener.onClick(v)
            }
        })
    }

    @JvmStatic
    @BindingAdapter("searchImageItems")
    fun setSendMessageItem(recyclerView: RecyclerView, items: MutableList<Document>?) {
        recyclerView.adapter?.let {
            if (it is MainImageAdapter) {
                if (items == null) it.clearItems() else it.addItems(items)
            }
        }
    }

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun setImageUrl(draweeView: SimpleDraweeView, imageUrl: String?) {
        imageUrl?.let {
            draweeView.setImageURI(imageUrl)
        }
    }

    @JvmStatic
    @BindingAdapter("imageUrl", "imageWidth", "imageHeight", requireAll = true)
    fun setImageUrlWithSize(draweeView: SimpleDraweeView, imageUrl: String?, width: Int, height: Int) {
        imageUrl?.let {
            draweeView.aspectRatio = width.toFloat() / height.toFloat()
            draweeView.setImageURI(imageUrl)
        }
    }

    @JvmStatic
    @BindingAdapter("notifyType")
    fun setNotifyType(imageView: ImageView, type: NotifyType) {
        when (type) {
            NotifyType.NOT_FOUND -> imageView.setImageResource(R.drawable.not_found)
            NotifyType.ERROR -> imageView.setImageResource(R.drawable.error)
            NotifyType.NONE -> imageView.setImageResource(0)
        }
    }

    @JvmStatic
    @BindingAdapter("notifyType")
    fun setNotifyType(textView: TextView, type: NotifyType) {
        when (type) {
            NotifyType.NOT_FOUND -> textView.setText(R.string.search_not_found)
            NotifyType.ERROR -> textView.setText(R.string.search_error)
            NotifyType.NONE -> textView.text = null
        }
    }

    @JvmStatic
    @BindingAdapter("imageSize")
    fun setImageSize(draweeView: SimpleDraweeView, document: Document) {
        document?.let {
            draweeView.layoutParams.run {
                width = document.width
                height = document.height
                draweeView.layoutParams = this
            }
        }
    }
}