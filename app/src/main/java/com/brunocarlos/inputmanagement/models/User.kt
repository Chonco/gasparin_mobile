package com.brunocarlos.inputmanagement.models

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.Serializable

class User(
    var id: Int,
    var name: String,
    var address: Address,
    var logoImg: String,
    var email: String,
    var phone: String,
    val userType: UserType,
    var foodType: List<String>
) : Serializable {
    fun getLogoAsBitmap(): Bitmap {
        val imageBytes = Base64.decode(logoImg, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }
}