package com.fauzan.githubuser.data.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object {
        private const val BASE_URL = "https://api.github.com/"
        private const val GITHUB_PAT = "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"

        fun getApiService(): ApiService {
            val client = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val req = chain.request()
                    val headers = req.newBuilder()
                        .addHeader("Authorization", GITHUB_PAT)
                        .build()
                    chain.proceed(headers)
                }
                .build()
            
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}