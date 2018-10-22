package com.anthony.kakaoimagesearch.common

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class EndlessRecyclerOnScrollListener(private val layoutManager: LinearLayoutManager, private val loadMoreListener: (() -> Unit)) : RecyclerView.OnScrollListener() {
    private var previousTotal = 0 // The total number of items in the dataset after the last load
    private var loading = true // True if we are still waiting for the last set of data to load.
    private var visibleThreshold = 2 // The minimum amount of items to have below your current scroll position before mLoading more.
    private var isFinished = false

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (isFinished) {
            return
        }

        val visibleItemCount = recyclerView!!.childCount
        val totalItemCount = layoutManager.itemCount
        if (totalItemCount < 1) {
            return
        }

        val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false
                previousTotal = totalItemCount
            }
        }
        if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {
            loading = true
            loadMoreListener.invoke()
        }
    }

    fun reset() {
        previousTotal = 0
        loading = false
        isFinished = false
    }

    fun setFinished() {
        isFinished = true
    }
}