package com.anthony.kakaoimagesearch.network

import com.anthony.kakaoimagesearch.model.ImageSearchResponseModel
import retrofit2.Callback

object KakaoApi {
    private val apiService = RetrofitManager.createServiceSession(KakaoSessionService::class.java)

    fun searchImage(callback: Callback<ImageSearchResponseModel>, request: MutableMap<String, Any>) {
        apiService?.searchImage(request)?.enqueue(callback)
    }
}