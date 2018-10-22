package com.anthony.kakaoimagesearch.network

import com.anthony.kakaoimagesearch.model.ImageSearchResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface KakaoSessionService {
    @GET("v2/search/image")
    fun searchImage(@QueryMap queryMap: MutableMap<String, Any>): Call<ImageSearchResponseModel>
}