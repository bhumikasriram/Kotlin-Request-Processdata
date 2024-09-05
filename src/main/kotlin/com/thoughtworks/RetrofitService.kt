package com.thoughtworks

import com.thoughtworks.api.ProductApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://localhost:3000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: ProductApi = retrofit.create(ProductApi::class.java)
}
