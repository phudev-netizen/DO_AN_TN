package com.example.lapstore.data.model

data class MomoResponse(
    val success: Boolean,
    val payUrl: String? = null,
    val message: String? = null
)
