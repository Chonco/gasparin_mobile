package com.brunocarlos.inputmanagement.adapters

import android.app.Activity
import android.content.Intent
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.brunocarlos.inputmanagement.R
import com.brunocarlos.inputmanagement.models.Offer
import com.brunocarlos.inputmanagement.models.OfferStatus
import com.brunocarlos.inputmanagement.models.UserType
import com.brunocarlos.inputmanagement.providers.OfferProvider
import com.brunocarlos.inputmanagement.shared.OfferDetailView
import com.brunocarlos.inputmanagement.util.OfferAdapterParams
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

class OfferAdapter(
    params: OfferAdapterParams
) : RecyclerView.Adapter<OfferAdapter.OfferViewHolder>() {

    private val offerList: MutableList<Offer>
    private val itemCardLayout: Int
    private val activity: Activity
    private val userType: UserType
    private val offersAccepted: Boolean

    init {
        this.offerList = params.offerList
        this.itemCardLayout = params.layout
        this.activity = params.activity
        this.userType = params.userType
        this.offersAccepted = params.offersAccepted
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    inner class OfferViewHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener, OnItemClickListener {

        private val offerName: TextView
        private val producerName: TextView
        private val offerPrice: TextView
        private val offerImg: ImageView
        private val offerTypeContainer: LinearLayout

        init {
            offerName = view.findViewById(R.id.offerName)
            producerName = view.findViewById(R.id.producerName)
            offerPrice = view.findViewById(R.id.offerPrice)
            offerImg = view.findViewById(R.id.imgOffer)
            offerTypeContainer = view.findViewById(R.id.offer_food_types_container)

            if (!offersAccepted) {
                view.setOnCreateContextMenuListener(this)
            }
        }

        fun render(offerModel: Offer) {
            offerImg.setImageBitmap(offerModel.getLogoAsBitmap())
            offerName.text = offerModel.name
            producerName.text = offerModel.sellerName
            try {
                val symbols = DecimalFormatSymbols()
                symbols.decimalSeparator = '.'
                val decimalFormat = DecimalFormat("$ #,###.00", symbols)
                offerPrice.text = decimalFormat.format(offerModel.price)
            } catch (e: Exception) {
            }

            drawFoodTypes(offerModel.foodType, offerTypeContainer)

            itemView.setOnClickListener {
                onItemClick(absoluteAdapterPosition)
            }
        }

        override fun onCreateContextMenu(
            contextMenu: ContextMenu,
            view: View,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            val offerSelected = offerList[absoluteAdapterPosition]

            contextMenu.setHeaderTitle(offerSelected.name)
            val inflater = activity.menuInflater

            if (userType == UserType.RESTAURANT)
                inflater.inflate(R.menu.item_offer_context_menu, contextMenu)
            else
                inflater.inflate(R.menu.seller_offers_list_context_menu, contextMenu)

            for (i in 0 until contextMenu.size()) {
                contextMenu.getItem(i).setOnMenuItemClickListener(this)
            }
        }

        override fun onMenuItemClick(menuItem: MenuItem): Boolean {
            val currentOffer = offerList[absoluteAdapterPosition]
            return when (menuItem.itemId) {
                R.id.accept_Offer -> {
                    currentOffer.status = OfferStatus.ACCEPTED
                    OfferProvider.updateOffer(currentOffer.id, currentOffer)
                    offerList.remove(currentOffer)
                    notifyItemRemoved(absoluteAdapterPosition)
                    true
                }
                R.id.reject_Offer -> {
                    OfferProvider.deleteOfferById(currentOffer.id)
                    offerList.remove(currentOffer)
                    notifyItemRemoved(absoluteAdapterPosition)
                    true
                }
                R.id.cancel_offer -> {
                    OfferProvider.deleteOfferById(currentOffer.id)
                    offerList.remove(currentOffer)
                    notifyItemRemoved(absoluteAdapterPosition)
                    true
                }
                else -> false
            }
        }

        override fun onItemClick(position: Int) {
            val intent = Intent(activity, OfferDetailView::class.java)
            intent.putExtra("offer", offerList[absoluteAdapterPosition])
            activity.startActivity(intent)
        }
    }

    private fun drawFoodTypes(foodTypes: List<String>, foodTypesContainer: LinearLayout) {
        //Creacion de LinearLayout
        val linearLayout = LinearLayout(activity)
        linearLayout.orientation = LinearLayout.HORIZONTAL
        linearLayout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        linearLayout.gravity = Gravity.END

        val size = foodTypes.size
        var i = 0

        while (i < size && i < 2) {
            //Asignamos el foodtype textview al linearLayout
            val textView = TextView(activity)
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams.marginEnd = 5
            textView.maxLines = 1
            textView.text = foodTypes[i]
            textView.layoutParams = layoutParams

            textView.setBackgroundResource(R.drawable.pill_offer_bg)
            linearLayout.addView(textView)
            i++
        }


        foodTypesContainer.addView(linearLayout)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        val layoutInflater = LayoutInflater.from(activity).inflate(itemCardLayout, parent, false)
        return OfferViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        holder.render(offerList[position])
    }

    override fun getItemCount(): Int = offerList.size

    fun setDataFiltered(offerList: List<Offer>) {
        this.offerList.clear()
        this.offerList.addAll(offerList)
        notifyDataSetChanged()
    }

}