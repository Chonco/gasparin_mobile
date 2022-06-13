package com.brunocarlos.inputmanagement.shared

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.view.children
import com.brunocarlos.inputmanagement.R
import com.brunocarlos.inputmanagement.models.Offer
import com.brunocarlos.inputmanagement.providers.OfferProvider
import java.io.ByteArrayOutputStream
import java.lang.NumberFormatException

class MakeOffer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_make_offer)

        val makeOfferButton = findViewById<Button>(R.id.submit_btn)
        makeOfferButton.setOnClickListener { makeOffer() }
    }

    private fun makeOffer() {
        val title = findViewById<EditText>(R.id.new_offer_title).text.toString()
        val foodTypesGrid = findViewById<GridLayout>(R.id.food_types_container)
        val foodTypesList = mutableListOf<String>()
        for (child in foodTypesGrid.children) {
            val checkBox = child as CheckBox
            if (checkBox.isChecked) {
                foodTypesList.add(checkBox.text.toString())
            }
        }

        val price: Double = try {
            findViewById<EditText>(R.id.price).text.toString().toDouble()
        } catch (exception: NumberFormatException) {
            0.0
        }

        val description = findViewById<EditText>(R.id.description).text.toString()

        if (
            title.isBlank() ||
            foodTypesList.isEmpty() ||
            (price == 0.0) ||
            description.isBlank()
        ) {
            showDialogInvalidOffer()
            return
        }

        val offer = Offer(
            0,
            title,
            price,
            "Gasparin",
            foodTypesList,
            description,
            getDefaultImageAsBase64(),
            false
        )

        OfferProvider.addOffer(offer)
        Toast.makeText(
            this,
            "Offer $title created",
            Toast.LENGTH_SHORT
        ).show()
        finish()
    }

    private fun showDialogInvalidOffer() {
        val alertBuilder = AlertDialog.Builder(this)
        alertBuilder.setTitle("Invalid Input")
        alertBuilder.setMessage("None input must remain empty")

        val dialog = alertBuilder.create()
        dialog.show()
    }

    private fun getDefaultImageAsBase64(): String {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.default_new_offer_image)
        val byteStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteStream)
        val byteArray = byteStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }
}