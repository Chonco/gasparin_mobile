package com.brunocarlos.inputmanagement.models

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.util.Base64
import java.io.Serializable

class Offer(
    val id: Int,
    val name: String,
    val price: Double,
    val sellerName: String,
    val foodType: List<String>,
    val productDescription: String,
    val productImg: String
):Serializable{
    fun getLogoAsBitmap(): Bitmap {
        val imageBytes = Base64.decode(productImg, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }
 }