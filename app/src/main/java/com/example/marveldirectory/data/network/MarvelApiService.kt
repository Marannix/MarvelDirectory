package com.example.marveldirectory.data.network

import com.example.marveldirectory.api.CharactersApi
import com.example.marveldirectory.data.network.response.CharactersResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import io.reactivex.Single
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val BASE_URL = "https://gateway.marvel.com/v1/public/"

interface MarvelApiService {

    //https://gateway.marvel.com/v1/public/characters?ts=1&apikey=f0bc3cc36c0b6b9a740687938bb7ec18&hash=8d04dfcad3727abed7b121c4b2d90178
    //    //character id = 1011334

    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    fun provideCharactersApi(retrofit: Retrofit): CharactersApi {
        return retrofit.create(CharactersApi::class.java)
    }

    fun charactersApi(): CharactersApi {
        return provideCharactersApi(provideRetrofit())
    }

//    companion object {
//        operator fun invoke(): MarvelApiService {
//            val requestInterceptor = Interceptor { chain ->
//                val url = chain.request()
//                    .url()
//                    .newBuilder()
//                    .addQueryParameter("apikey", API_KEY)
//                    .addQueryParameter("hash", HASH_KEY)
//                    .addQueryParameter("ts", TIME_STAMP)
//                    .build()
//
//                val request = chain.request()
//                    .newBuilder()
//                    .url(url)
//                    .build()
//
//                return@Interceptor chain.proceed(request)
//
//            }
//
//            val okHttpClient = OkHttpClient.Builder()
//                .addInterceptor(requestInterceptor)
//                .build()
//
//            return Retrofit.Builder()
//                .client(okHttpClient)
//                .baseUrl("https://gateway.marvel.com/v1/public/")
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//                .create(MarvelApiService::class.java)
//        }
//    }


}