package com.brunocarlos.inputmanagement.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brunocarlos.inputmanagement.models.Offer

class OfferAdapter(val offerList:List<Offer>): RecyclerView.Adapter<OfferViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {

    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return offerList.size
    }
}