package com.brunocarlos.inputmanagement.restaurant

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brunocarlos.inputmanagement.R
import com.brunocarlos.inputmanagement.adapters.OfferAdapter
import com.brunocarlos.inputmanagement.models.Offer
import com.brunocarlos.inputmanagement.models.OfferStatus
import com.brunocarlos.inputmanagement.models.UserType
import com.brunocarlos.inputmanagement.providers.OfferProvider
import com.brunocarlos.inputmanagement.util.OfferAdapterParams


class AvailableOffersFragment : Fragment() {
    private lateinit var customAdapter: OfferAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_available_offers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.available_offers_recycler_view)
        customAdapter = OfferAdapter(
            OfferAdapterParams(
                OfferProvider.getOngoingOffers() as MutableList<Offer>,
                R.layout.item_offer,
                this.requireActivity(),
                false,
                UserType.RESTAURANT
            )
        )
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = customAdapter
        }

        val filterBtn = view.findViewById<Button>(R.id.filter_btn)
        filterBtn.setOnClickListener { filterList() }
    }

    private fun filterList() {
        val filterAlert = AlertDialog.Builder(requireActivity())
        filterAlert.setTitle("Filter")
        filterAlert.setView(
            requireActivity().layoutInflater.inflate(
                R.layout.seller_offer_filter_dialog,
                null
            )
        )
            .setPositiveButton(
                R.string.filter_accept_filters
            ) { dialogInterface, _ ->
                run {
                    val alertDialog = (dialogInterface as AlertDialog)
                    val offeredCheckBox =
                        alertDialog.findViewById<CheckBox>(R.id.offered_checkbox)
                    val rejectedCheckBox =
                        alertDialog.findViewById<CheckBox>(R.id.rejected_checkbox)
                    val canceledCheckbox =
                        alertDialog.findViewById<CheckBox>(R.id.canceled_checkbox)
                    val deliveredCheckbox =
                        alertDialog.findViewById<CheckBox>(R.id.delivered_checkbox)

                    val statusList = mutableListOf<OfferStatus>()
                    if (offeredCheckBox.isChecked)
                        statusList.add(OfferStatus.OFFERED)
                    if (rejectedCheckBox.isChecked)
                        statusList.add(OfferStatus.REJECTED)
                    if (canceledCheckbox.isChecked)
                        statusList.add(OfferStatus.CANCELED)
                    if (deliveredCheckbox.isChecked)
                        statusList.add(OfferStatus.DELIVERED)

                    customAdapter.setDataFiltered(
                        OfferProvider.getOffersWithStatus(statusList)
                    )
                }
            }
        filterAlert.create().show()
    }
}