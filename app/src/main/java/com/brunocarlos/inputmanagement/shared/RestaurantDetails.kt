package com.brunocarlos.inputmanagement.shared

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.brunocarlos.inputmanagement.R
import com.brunocarlos.inputmanagement.models.User

class RestaurantDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_details)

        val restaurant = intent.getSerializableExtra("restaurant") as User

        val logoContainer = findViewById<ImageView>(R.id.restaurant_details_logo)
        logoContainer.setImageBitmap(restaurant.getLogoAsBitmap())

        val restaurantName = findViewById<TextView>(R.id.restaurant_details_name)
        restaurantName.text = restaurant.name

        val foodTypesContainer =
            findViewById<LinearLayout>(R.id.restaurant_details_food_types_container)
        drawFoodTypes(restaurant.foodType, foodTypesContainer)

        val address = findViewById<TextView>(R.id.restaurant_details_address)
        address.text = restaurant.address.longAddress

        val offerButton = findViewById<Button>(R.id.restaurant_details_make_offer)
        offerButton.setOnClickListener { sendToAddOfferActivity() }
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
            textView.setBackgroundResource(R.drawable.pill_bg)
            foodTypesContainer.addView(textView)
        }
    }

    private fun sendToAddOfferActivity() {

    }
}