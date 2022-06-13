package com.brunocarlos.inputmanagement.shared

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.util.DisplayMetrics
import android.view.Gravity
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

        //LinearLayout
        val foodTypeContainer =
            findViewById<LinearLayout>(R.id.offer_details_food_type_container)
        drawFoodTypes(offer.foodType, foodTypeContainer)

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

                textView.setBackgroundResource(R.drawable.pill_offer_bg)
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

    private fun acceptOffer() {
        offer.isAccepted = true
        OfferProvider.updateOffer(offer.id, offer)
        finish()
    }

    private fun rejectOffer() {
        OfferProvider.deleteOfferById(offer.id)
        finish()
    }
}