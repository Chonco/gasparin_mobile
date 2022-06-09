package com.brunocarlos.inputmanagement.providers

import com.brunocarlos.inputmanagement.R
import com.brunocarlos.inputmanagement.models.Offer

class OfferProvider {
    companion object{
        val offerList = listOf<Offer>(
            Offer(
                1,
            "cherry",
            32.99,
            "GasparinFarms",
            "Fruits",
            "Big Red Cherry",
            R.drawable.cherry_bg)
        )
    }
}