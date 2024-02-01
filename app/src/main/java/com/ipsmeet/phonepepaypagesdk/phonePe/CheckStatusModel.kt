package com.nobesityIndia.fitness.model.phonePe

data class CheckStatusModel(
    val code: String,
    val `data`: Data,
    val message: String,
    val success: Boolean
)