package com.brunocarlos.inputmanagement.shared

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.brunocarlos.inputmanagement.R
import com.brunocarlos.inputmanagement.models.Offer
import com.brunocarlos.inputmanagement.providers.OfferProvider
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

class OfferDetailView : AppCompatActivity() {
    private lateinit var offer: Offer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offer_detail_view)

        offer = intent.getSerializableExtra("offer") as Offer

        //Img
        val imgContainer = findViewById<ImageView>(R.id.offer_details_img)
        imgContainer.setImageBitmap(offer.getLogoAsBitmap())

        //Name
        val offerName = findViewById<TextView>(R.id.offer_details_name)
        offerName.text = offer.name

        //LinearLayout
        val foodTypeContainer =
            findViewById<LinearLayout>(R.id.offer_details_food_type_container)
        drawFoodTypes(offer.foodType, foodTypeContainer)

        //Price
        val offerPrice = findViewById<TextView>(R.id.offer_details_price)
        try {
            val symbols = DecimalFormatSymbols()
            symbols.decimalSeparator = '.'
            symbols.groupingSeparator = '\\'
            val decimalFormat = DecimalFormat("$ #,###.00", symbols)
            offerPrice.text = decimalFormat.format(offer.price)
        } catch (e: Exception) {
        }

        val offerSeller = findViewById<TextView>(R.id.offer_Details_seller)
        offerSeller.text = offer.sellerName

        val offerDescription = findViewById<TextView>(R.id.offer_details_description)
        offerDescription.text = offer.productDescription

        val acceptOfferBtn = findViewById<Button>(R.id.offer_details_accept_button)
        val rejectOfferBtn = findViewById<Button>(R.id.offer_details_reject_button)

        acceptOfferBtn.setOnClickListener { acceptOffer() }
        rejectOfferBtn.setOnClickListener { rejectOffer() }

        if (offer.isAccepted) {
            val buttonsContainer = findViewById<LinearLayout>(R.id.offer_details_buttons_container)
            buttonsContainer.visibility = View.GONE
        }
    }

    private fun drawFoodTypes(foodTypes: List<String>, foodTypesContainer: LinearLayout) {
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.marginEnd = 5

        for (i in foodTypes.indices) {
            val textView = TextView(this)
            textView.text = foodTypes[i]
            textView.layoutParams = layoutParams
            textView.setBackgroundResource(R.drawable.pill_offer_bg)
            foodTypesContainer.addView(textView)
        }
    }

    private fun acceptOffer() {
        offer.isAccepted = true
        OfferProvider.updateOffer(offer.id, offer)
        finishActivity(1)
    }

    private fun rejectOffer() {
        OfferProvider.deleteOfferById(offer.id)
        finishActivity(0)
    }
}