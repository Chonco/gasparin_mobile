package com.brunocarlos.inputmanagement.models

data class Offer (
    val id: Int,
    val name: String,
    val price: Double,
    val sellerName: String,
    val foodType: List<String>,
    val productDescription: String,
    val productImg: Int
)