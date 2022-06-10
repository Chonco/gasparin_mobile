package com.brunocarlos.inputmanagement.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.brunocarlos.inputmanagement.R
import com.brunocarlos.inputmanagement.models.Offer
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

class OfferViewHolder (view : View): RecyclerView.ViewHolder(view){

    private val offerName = view.findViewById<TextView>(R.id.offerName)
    private val producerName = view.findViewById<TextView>(R.id.producerName)
    private val offerPrice = view.findViewById<TextView>(R.id.offerPrice)
    private val offerImg = view.findViewById<ImageView>(R.id.imgOffer)

    fun render(offerModel : Offer){
        offerName.text = offerModel.name
        producerName.text = offerModel.sellerName
        try {
            val symbols = DecimalFormatSymbols()
            symbols.decimalSeparator = '.'
            symbols.groupingSeparator = '\\'
            val decimalFormat = DecimalFormat("$ #,###.00", symbols)
            offerPrice.text = decimalFormat.format(offerModel.price)
        }catch (e:Exception){}
        offerImg.setImageBitmap(offerModel.getLogoAsBitmap())
    }
}