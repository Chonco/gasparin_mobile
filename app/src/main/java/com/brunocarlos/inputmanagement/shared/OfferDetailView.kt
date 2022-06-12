package com.brunocarlos.inputmanagement.shared

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.brunocarlos.inputmanagement.R
import com.brunocarlos.inputmanagement.models.Offer
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

class OfferDetailView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offer_detail_view)

        val offer = intent.getSerializableExtra("offer") as Offer

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
        }catch (e:Exception){}

        val offerSeller = findViewById<TextView>(R.id.offer_Details_seller)
        offerSeller.text = offer.sellerName

        val offerDescription = findViewById<TextView>(R.id.offer_details_description)
        offerDescription.text = offer.productDescription
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
            textView.maxLines = 1
            textView.layoutParams = layoutParams
            textView.setBackgroundResource(R.drawable.pill_offer_bg)
            foodTypesContainer.addView(textView)
        }
    }
}