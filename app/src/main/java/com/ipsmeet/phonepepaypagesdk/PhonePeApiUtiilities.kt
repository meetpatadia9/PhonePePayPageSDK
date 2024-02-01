package com.ipsmeet.phonepepaypagesdk

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PhonePeApiUtiilities {

    fun getApiInterface(): RetrofitInterface {
        return Retrofit.Builder()
            .baseUrl("https://api-preprod.phonepe.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitInterface::class.java)
    }

}