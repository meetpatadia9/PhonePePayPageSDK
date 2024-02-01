package com.nobesityIndia.fitness.model.phonePe

data class PaymentInstrument(
    val ifsc: String,
    val maskedAccountNumber: String,
    val upiTransactionId: Any,
    val utr: String,
    val vpa: Any
)