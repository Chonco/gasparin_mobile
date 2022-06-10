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
            listOf<String>("Fruit", "Fresh"),
            "Big Red Cherry",
            "asdasdasd")
        )
    }
}