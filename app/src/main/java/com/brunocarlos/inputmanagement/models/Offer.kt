package com.brunocarlos.inputmanagement.models

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.util.Base64
import java.io.Serializable

class Offer(
    var id: Int,
    var name: String,
    var price: Double,
    val sellerName: String,
    var foodType: List<String>,
    var productDescription: String,
    var productImg: String,
    var isAccepted: Boolean
) : Serializable {
    fun getLogoAsBitmap(): Bitmap {
        val imageBytes = Base64.decode(productImg, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }
}