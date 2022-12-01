package com.example.networkretrofit.network.retrofit

import com.example.networkretrofit.network.util.NetworkConstants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Client {
    object RetrofitClient {
        val retrofit: Service by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Service::class.java)
        }
    }
}