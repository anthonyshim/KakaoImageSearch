package com.anthony.kakaoimagesearch.main

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.text.Editable
import android.util.Log
import com.anthony.kakaoimagesearch.common.EndlessRecyclerOnScrolInterface
import com.anthony.kakaoimagesearch.common.NotifyType
import com.anthony.kakaoimagesearch.model.Document
import com.anthony.kakaoimagesearch.model.ImageSearchResponseModel
import com.anthony.kakaoimagesearch.network.KakaoApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.concurrent.timerTask

class MainViewModel(private val endlessRecyclerOnScrolInterface: EndlessRecyclerOnScrolInterface) {
    companion object {
        private const val API_REQUEST_QUERY = "query"
        private const val API_REQUEST_SORT = "sort"
        private const val API_REQUEST_PAGE = "page"
        private const val API_REQUEST_SIZE = "size"

        enum class ImageSearchSort {
            ACCURACY,
            RECENCY
        }
    }

    var searchImageItems = ObservableField<List<Document>>()
    var notifyType = ObservableField<NotifyType>(NotifyType.NONE)
    var isShowingrPogressBar = ObservableBoolean(false)
    private val requestParameters = HashMap<String, Any>()
    private var timer: Timer? = null

    init {
        initRequestParameters()
    }

    fun afterTextChanged(editable: Editable?) {
        Log.d("TAG", "afterTextChanged() editable : " + editable.toString())
        timer?.let {
            it.cancel()
        }
        timer = Timer().apply {
            schedule(timerTask {
                initRequestParameters()
                searchImageItems.set(null)
                endlessRecyclerOnScrolInterface.resetScroll()
                requestParameters[API_REQUEST_QUERY] = editable.toString()
                if (editable.toString().isNotEmpty()) {
                    searchImage()
                }
            }, 1000)
        }
    }

    fun searchNextImage() {
        requestParameters[API_REQUEST_PAGE] = (requestParameters[API_REQUEST_PAGE] as Int).inc()
        searchImage()
    }

    private fun searchImage() {
        isShowingrPogressBar.set(true)
        KakaoApi.searchImage(object : Callback<ImageSearchResponseModel> {
            override fun onFailure(call: Call<ImageSearchResponseModel>, t: Throwable) {
                isShowingrPogressBar.set(false)
                notifyType.set(NotifyType.ERROR)
            }

            override fun onResponse(call: Call<ImageSearchResponseModel>, response: Response<ImageSearchResponseModel>) {
                isShowingrPogressBar.set(false)
                if (response.isSuccessful) {
                    val responseModel = response.body()
                    if (responseModel is ImageSearchResponseModel) {
                        if (responseModel.meta.total_count > 0) {
                            searchImageItems.set(responseModel.documents)
                            notifyType.set(NotifyType.NONE)
                        } else {
                            notifyType.set(NotifyType.NOT_FOUND)
                        }
                        if (responseModel.meta.is_end) {
                            endlessRecyclerOnScrolInterface.loadingFinish()
                        }
                    }
                } else {
                    notifyType.set(NotifyType.ERROR)
                }
            }

        }, requestParameters)
    }

    private fun initRequestParameters() {
        requestParameters[API_REQUEST_PAGE] = 1
        requestParameters[API_REQUEST_SIZE] = 10
        requestParameters[API_REQUEST_SORT] = MainViewModel.Companion.ImageSearchSort.ACCURACY
    }
}