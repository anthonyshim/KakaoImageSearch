package com.anthony.kakaoimagesearch.network

import com.anthony.kakaoimagesearch.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitManager {
    private const val TIMEOUT_MILLISECONDS = 5000L
    private const val HEADER_AUTHORIZATION = "Authorization"

    private var retrofitSession: Retrofit? = null
    private var baseUrl: String? = null
        set(value) {
            field = value
            retrofitSession = null
        }
    private var authorizationKey: String? = null

    fun initialize(baseUrl: String, authorizationKey: String) {
        this.baseUrl = baseUrl
        this.authorizationKey = authorizationKey
    }

    fun <T : Any> createServiceSession(service: Class<T>): T? {
        return createService(getRetrofitSession(), service)
    }

    private fun getRetrofitSession(): Retrofit {
        if (null == retrofitSession) {
            retrofitSession = createRetrofit()
        }

        return retrofitSession!!
    }

    private fun <T : Any> createService(retrofit: Retrofit?, service: Class<T>): T? {
        return retrofit?.create(service)
    }

    private fun createRetrofit(): Retrofit {
        val okHttpClient = createOkHttpClient()
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient).build()
    }

    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
                .connectTimeout(TIMEOUT_MILLISECONDS, TimeUnit.MILLISECONDS)
                .readTimeout(TIMEOUT_MILLISECONDS, TimeUnit.MILLISECONDS)
                .writeTimeout(TIMEOUT_MILLISECONDS, TimeUnit.MILLISECONDS)
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
                })
                .addInterceptor { chain: Interceptor.Chain ->
                    chain.proceed(
                            chain.request().newBuilder().apply {
                                authorizationKey?.let {
                                    addHeader(HEADER_AUTHORIZATION, it)
                                }
                            }.build()
                    )
                }
                .build()
    }
}