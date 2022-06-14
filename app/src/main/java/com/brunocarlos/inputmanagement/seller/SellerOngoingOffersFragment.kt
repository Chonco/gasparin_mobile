package com.brunocarlos.inputmanagement.seller

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brunocarlos.inputmanagement.R
import com.brunocarlos.inputmanagement.adapters.OfferAdapter
import com.brunocarlos.inputmanagement.models.Offer
import com.brunocarlos.inputmanagement.models.UserType
import com.brunocarlos.inputmanagement.providers.OfferProvider
import com.brunocarlos.inputmanagement.util.OfferAdapterParams

class SellerOngoingOffersFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_seller_ongoing_offers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.seller_ongoing_offers_recycler_view)
        val customAdapter = OfferAdapter(
            OfferAdapterParams(
                OfferProvider.getAcceptedOffers() as MutableList<Offer>,
                R.layout.item_offer,
                this.requireActivity(),
                true,
                UserType.SELLER
            )
        )
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = customAdapter
        }
    }
}