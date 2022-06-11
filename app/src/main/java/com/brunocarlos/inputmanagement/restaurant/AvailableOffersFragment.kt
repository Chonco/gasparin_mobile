package com.brunocarlos.inputmanagement.restaurant

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brunocarlos.inputmanagement.R
import com.brunocarlos.inputmanagement.adapters.OfferAdapter
import com.brunocarlos.inputmanagement.models.Offer
import com.brunocarlos.inputmanagement.providers.OfferProvider
import com.brunocarlos.inputmanagement.shared.OfferDetailView


class AvailableOffersFragment : Fragment() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<OfferAdapter.OfferViewHolder> ? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_available_offers, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.avilable_offers_recycler_view)
        val customAdapter = OfferAdapter(
            OfferProvider.offerList,
            R.layout.item_offer,
            this.requireActivity()
        )
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = customAdapter
        }
    }
}