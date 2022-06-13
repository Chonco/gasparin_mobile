package com.brunocarlos.inputmanagement.util

import android.app.Activity
import com.brunocarlos.inputmanagement.models.Offer
import com.brunocarlos.inputmanagement.models.UserType

class OfferAdapterParams(
    val offerList: MutableList<Offer>,
    val layout: Int,
    val activity: Activity,
    val offersAccepted: Boolean,
    val userType: UserType
)