package com.brunocarlos.inputmanagement.adapters

import android.app.Activity
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.brunocarlos.inputmanagement.R
import com.brunocarlos.inputmanagement.models.User

class RestaurantListAdapter(
    restaurants: List<User>,
    layout: Int,
    activity: Activity
) : RecyclerView.Adapter<RestaurantListAdapter.ViewHolder>() {

    private val restaurants: List<User>
    private val itemCardLayout: Int
    private val activity: Activity

    init {
        this.restaurants = restaurants
        this.itemCardLayout = layout
        this.activity = activity
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        private val logo: ImageView
        private val name: TextView
        private val foodTypesContainer: LinearLayout
        private val address: TextView

        init {
            logo = view.findViewById(R.id.restaurant_logo)
            name = view.findViewById(R.id.restaurant_name)
            foodTypesContainer = view.findViewById(R.id.restaurant_food_types_container)
            address = view.findViewById(R.id.restaurant_address)

            view.setOnCreateContextMenuListener(this)
        }

        fun bind(restaurant: User) {
            logo.setImageBitmap(restaurant.getLogoAsBitmap())
            name.text = restaurant.name
            address.text = restaurant.address

            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams.marginEnd = 5

            for (i in 0 until restaurant.foodType.size) {
                val textView = TextView(activity)
                textView.text = restaurant.foodType[i]
                textView.layoutParams = layoutParams
                foodTypesContainer.addView(textView)
            }
        }

        override fun onCreateContextMenu(
            contextMenu: ContextMenu,
            view: View,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            val restaurantSelected = restaurants[this.absoluteAdapterPosition]

            contextMenu.setHeaderTitle(restaurantSelected.name)
            val inflater = activity.menuInflater
            inflater.inflate(R.menu.restaurant_list_context_menu, contextMenu)

            for (i in 0 until contextMenu.size()) {
                contextMenu.getItem(i).setOnMenuItemClickListener(this)
            }
        }

        override fun onMenuItemClick(menuItem: MenuItem): Boolean {
            return when (menuItem.itemId) {
                R.id.restaurant_details -> {
                    true
                }
                R.id.restaurant_make_offer -> {
                    true
                }
                else -> false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(activity).inflate(itemCardLayout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(restaurants[position])
    }

    override fun getItemCount(): Int = restaurants.size
}