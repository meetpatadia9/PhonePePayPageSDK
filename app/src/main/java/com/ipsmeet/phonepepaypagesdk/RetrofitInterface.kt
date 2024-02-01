package com.ipsmeet.phonepepaypagesdk

import com.nobesityIndia.fitness.model.PhonePeReq.PhonePeReq
import com.nobesityIndia.fitness.model.PhonePeRes.PhonePeRes
import com.nobesityIndia.fitness.model.phonePe.CheckStatusModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.Path

interface RetrofitInterface {

    @POST("apis/pg-sandbox/pg/v1/pay")
    suspend fun initializePayment(
        @Header("Content-Type") contentType: String,
        @Header("X-VERIFY") x_verify: String,
        @Body data: PhonePeReq
    ): Response<PhonePeRes>

    @GET("apis/pg-sandbox/pg/v1/status/{merchantId}/{transactionId}")
    suspend fun checkStatus(
        @Path("merchantId") merchantId: String,
        @Path("transactionId") transactionId: String,
        @HeaderMap headers: Map<String, String>,
    ): Response<CheckStatusModel>

}