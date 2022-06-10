package com.brunocarlos.inputmanagement.models

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.Serializable

class User(
    val id: Int,
    val name: String,
    val address: Address,
    val logoImg: String,
    val email: String,
    val phone: String,
    val userType: UserType,
    val foodType: List<String>
) : Serializable {
    fun getLogoAsBitmap(): Bitmap {
        val imageBytes = Base64.decode(logoImg, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }
}