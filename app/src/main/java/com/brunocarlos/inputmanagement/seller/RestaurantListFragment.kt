package com.brunocarlos.inputmanagement.seller

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.brunocarlos.inputmanagement.R
import com.brunocarlos.inputmanagement.adapters.RestaurantListAdapter
import com.brunocarlos.inputmanagement.providers.RestaurantsProvider

class RestaurantListFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_restaurant_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.restaurant_list_recycler_view)
        val customAdapter = RestaurantListAdapter(
            RestaurantsProvider.getAllRestaurants(),
            R.layout.restaurant_list_item_view,
            this.requireActivity()
        )

        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = customAdapter
        }

    }

}