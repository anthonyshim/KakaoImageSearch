package com.anthony.kakaoimagesearch

import android.app.Application
import com.anthony.kakaoimagesearch.network.RetrofitManager

class KakaoImageSearchApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        RetrofitManager.initialize("https://dapi.kakao.com/", String.format("KakaoAK %s", getString(R.string.kakao_rest_api_key)))
    }
}