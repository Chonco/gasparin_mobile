package com.brunocarlos.inputmanagement.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.brunocarlos.inputmanagement.R
import com.brunocarlos.inputmanagement.models.Offer

class OfferViewHolder (view : View): RecyclerView.ViewHolder(view){

    val offerName = view.findViewById<TextView>(R.id.offerName)
    val producerName = view.findViewById<TextView>(R.id.producerName)
    val offerPrice = view.findViewById<TextView>(R.id.offerPrice)
    val offerImg = view.findViewById<ImageView>(R.id.imgOffer)

    fun render(offerModel : Offer){
        offerName.text = offerModel.name
        producerName.text = offerModel.sellerName
        offerPrice.text = offerModel.price.toString()

    }
}