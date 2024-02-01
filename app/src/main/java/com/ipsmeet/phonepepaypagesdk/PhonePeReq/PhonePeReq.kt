package com.nobesityIndia.fitness.model.PhonePeReq

data class PhonePeReq(
    val amount: Int,
    val callbackUrl: String,
    val deviceContext: DeviceContext,
    val merchantId: String,
    val merchantTransactionId: String,
    val merchantUserId: String,
    val mobileNumber: String,
    val paymentInstrument: PaymentInstrument
)