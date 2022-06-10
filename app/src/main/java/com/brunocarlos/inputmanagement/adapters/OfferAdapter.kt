package com.brunocarlos.inputmanagement.adapters

import android.app.Activity
import android.view.*
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.brunocarlos.inputmanagement.R
import com.brunocarlos.inputmanagement.models.Offer

class OfferAdapter(offerList:List<Offer>,
                   layout: Int,
                   activity: Activity
): RecyclerView.Adapter<OfferViewHolder>(){

    private val offerList : List<Offer>
    private val itemCardLayout : Int
    private val activity: Activity

    init {
        this.offerList = offerList
        this.itemCardLayout = layout
        this.activity = activity
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        val layoutInflater = LayoutInflater.from(activity).inflate(itemCardLayout,parent,false)
        return OfferViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        holder.render(offerList[position])
    }

    override fun getItemCount(): Int = offerList.size

}