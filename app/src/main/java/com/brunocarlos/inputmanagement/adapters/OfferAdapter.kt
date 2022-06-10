package com.brunocarlos.inputmanagement.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brunocarlos.inputmanagement.R
import com.brunocarlos.inputmanagement.models.Offer

class OfferAdapter(private val offerList:List<Offer>): RecyclerView.Adapter<OfferViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return OfferViewHolder(layoutInflater.inflate(R.layout.item_offer, parent, false))
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        val item = offerList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = offerList.size

}