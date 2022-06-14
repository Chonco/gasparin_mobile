package com.brunocarlos.inputmanagement.shared

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.brunocarlos.inputmanagement.R
import com.brunocarlos.inputmanagement.models.User

class RestaurantDetails : AppCompatActivity() {
    lateinit var restaurant: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_details)

        restaurant = intent.getSerializableExtra("restaurant") as User

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

        //Creamos lista de LinearLayouts
        val linearLayoutList: MutableList<LinearLayout> = mutableListOf()

        val displayMetrics: DisplayMetrics = foodTypesContainer.resources.displayMetrics

        val totalAvailableWidth =
            (displayMetrics.widthPixels * 0.8).toInt()

        val size = foodTypes.size
        var i = 0

        //mientras i sea menor a size se crearan linearLayouts horizontales
        while (i < size) {


            //Creacion de LinearLayout
            val linearLayout = LinearLayout(this)
            linearLayout.orientation = LinearLayout.HORIZONTAL

            linearLayout.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            linearLayout.gravity = Gravity.FILL_HORIZONTAL

            while (true) {
                //Asignamos el foodtype textview al linearLayout
                var textView = TextView(this)
                val layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                layoutParams.topMargin = 5
                layoutParams.marginEnd = 10
                layoutParams.topMargin = 10
                textView.maxLines = 1
                textView.text = foodTypes[i]
                textView.layoutParams = layoutParams

                textView.setBackgroundResource(R.drawable.pill_bg)
                linearLayout.addView(textView)

                linearLayout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
                val occupiedWidth = linearLayout.measuredWidth

                //si el ancho excede al total disponible entonces se remueve el ultimo foodtype añadido y se mantiene
                //en el linear layout actual a ser añadido al linearlayout principal
                if (occupiedWidth > totalAvailableWidth) {

                    //Si un foodtype excede el limite maximo entonces incrementamos el contador i entonces
                    //removemos el ultimo foodtype añadido
                    if (linearLayout.childCount == 1) {
                        i++

                        //Si no, no incrementa el contador i ya que queremos que el ultimo foodtype sea incluido en
                        //la siguiente iteracion
                    } else {
                        linearLayout.removeView(textView)
                    }
                    linearLayoutList.add(linearLayout)
                    break

                } else {
                    //si el item es el ultimo en la lista entonces retiene el layout ya que el bucle
                    //se romperá despues de esto
                    if (i == (size - 1)) {
                        linearLayoutList.add(linearLayout)
                    }
                    //Incrementamos el contador para movernos al siguiente item
                    i++
                }

                if (i >= size) {
                    break
                }
            }
        }

        for (l in linearLayoutList) {
            foodTypesContainer.addView(l)
        }
    }

    private fun sendToAddOfferActivity() {
        val intent = Intent(this, MakeOffer::class.java)
        intent.putExtra("restaurant", restaurant)
        startActivity(intent)
    }
}