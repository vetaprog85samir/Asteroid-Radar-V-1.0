package com.egyptfwd.asteroidradar.api

import com.egyptfwd.asteroidradar.utils.Constants.BASE_URL
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(loggingInterceptor())
    .connectTimeout(30, TimeUnit.SECONDS) //Backend is really slow
    .writeTimeout(30, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .build()

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .client(okHttpClient)
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface RetrofitServices {

    @GET("planetary/apod/")
    suspend fun getPictureOfDay(@Query("api_key") apiKey: String): PicOfTheDayDto

    @GET("neo/rest/v1/feed/")
    suspend fun getAsteroids(
        @Query("api_key") apiKey: String
    ): String

}

object ApiClient {
    val retrofitService : RetrofitServices by lazy {
        retrofit.create(RetrofitServices::class.java)
    }
}

private fun loggingInterceptor(): HttpLoggingInterceptor{
    val interceptor = HttpLoggingInterceptor()
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

    return interceptor
}