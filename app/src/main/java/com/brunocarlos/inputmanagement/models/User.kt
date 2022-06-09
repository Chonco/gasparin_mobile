package com.brunocarlos.inputmanagement.models

class User(
    val id: Int,
    val name: String,
    val address: String,
    val logoImg: String,
    email: String,
    phone: String,
    userType: UserType,
    foodType: List<String>
)